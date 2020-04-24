package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import lombok.val;
import org.chimerax.prometheus.api.exception.NotFoundException;
import org.chimerax.prometheus.api.exception.UnauthorizedException;
import org.chimerax.prometheus.entity.*;
import org.chimerax.prometheus.repository.ClientRepository;
import org.chimerax.prometheus.repository.GrantedAccessRepository;
import org.chimerax.prometheus.repository.UserRepository;
import org.chimerax.prometheus.security.JWTServiceHelper;
import org.chimerax.prometheus.security.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 10:48 PM
 */

@Service
@AllArgsConstructor
public class AuthorizationService {

    private ClientRepository clientRepository;
    private GrantedAccessRepository grantedAccessRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTServiceHelper jwtServiceHelper;
    private PasswordEncoder passwordEncoder;

    public String authorize(final String clientId, final List<Scope> scope) {
        // client
        final Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
        final Client client = clientOptional.orElseThrow(NotFoundException::new);

        // filter the scope that the client tries to access but did not register
        filterMaliciousScope(client, scope);

        // authorities
        final Set<Authority> authorities = scope.stream()
                .map(Authority::getForScope)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        // username
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // code
        final String code = UUID.randomUUID().toString();

        final GrantedAccess grantedAccess = new GrantedAccess()
                .setClientId(clientId)
                .setUsername(username)
                .setAuthorities(authorities)
                .setCode(code);

        grantedAccessRepository.save(grantedAccess);

        return code;
    }

    private void filterMaliciousScope(final Client client, final Collection<Scope> scopes) {
        final List<Scope> malicious = new ArrayList<>();

        val clientScope = client.getScope();

        for (final Scope scope : scopes) {
            if (!clientScope.contains(scope)) {
                malicious.add(scope);
            }
        }
        if (malicious.size() > 0) {
            throw new UnauthorizedException("Client " + client.getClientId() + " can't access " + malicious);
        }
    }

    public String token(final String clientId, final String secret, final String code) {
        // client
        final Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
        final Client client = clientOptional.orElseThrow(NotFoundException::new);
        checkClientSecret(client, secret);

        // ownership of the client for the code
        final Optional<GrantedAccess> grantedAccessOptional = grantedAccessRepository.findByCode(code);
        final GrantedAccess grantedAccess = grantedAccessOptional.orElseThrow(NotFoundException::new);
        checkClientOwnership(client, grantedAccess);

        // the availability of the user
        final Optional<User> userOptional = userRepository.findByUsername(grantedAccess.getUsername());
        final User user = userOptional.orElseThrow(NotFoundException::new);
        checkUserAvailability(user);

        // authorities of the generated token
        final List<GrantedAuthority> authorities = new ArrayList<>(grantedAccess.getAuthorities());

        final UserDetails userDetails = UserDetailsImpl.builder()
                .username(user.getUsername())
                .authorities(authorities)
                .build();

        return jwtServiceHelper.generateToken(userDetails);
    }

    private void checkClientSecret(final Client client, final String secret) {
        if (!passwordEncoder.matches(secret, client.getSecret())) {
            throw new UnauthorizedException("Bad client secret");
        }
    }

    private void checkClientOwnership(final Client client, final GrantedAccess grantedAccess) {
        if (!grantedAccess.getClientId().equals(client.getClientId())) {
            throw new UnauthorizedException("Client can not access this code");
        }
    }

    private void checkUserAvailability(final User user) {
        if (!user.isActive()) {
            throw new UnauthorizedException("User is not active");
        }
    }

}
