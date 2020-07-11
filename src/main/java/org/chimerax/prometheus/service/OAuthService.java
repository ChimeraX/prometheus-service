package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import lombok.val;
import org.chimerax.common.exception.NotFoundException;
import org.chimerax.common.exception.UnauthorizedException;
import org.chimerax.common.security.jwt.JWTService;
import org.chimerax.common.security.jwt.JWTToken;
import org.chimerax.common.security.jwt.UserDetailsImpl;
import org.chimerax.prometheus.api.dto.UserInfoDTO;
import org.chimerax.prometheus.entity.*;
import org.chimerax.prometheus.repository.ClientRepository;
import org.chimerax.prometheus.repository.GrantedAccessRepository;
import org.chimerax.prometheus.repository.UserRepository;
import org.chimerax.prometheus.service.userinfo.UserInfoService;
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
public class OAuthService {

    private UserInfoService userInfoService;
    private ClientRepository clientRepository;
    private GrantedAccessRepository grantedAccessRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTService jwtService;

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
        final JWTToken token = (JWTToken) SecurityContextHolder.getContext().getAuthentication();
        final String username = token.getName();
        final JWTToken.Details details = token.getDetails();
        details.getCookies();

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

    public String generateToken(final String clientId, final String secret, final String code) {
        // client
        final Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
        final Client client = clientOptional
                .orElseThrow(() -> new NotFoundException("No client: " + clientId));
        checkClientCredentials(client, secret);

        // ownership of the client for the code
        final Optional<GrantedAccess> grantedAccessOptional = grantedAccessRepository.findByCode(code);
        final GrantedAccess grantedAccess = grantedAccessOptional
                .orElseThrow(() -> new NotFoundException("No code: " + code));
        checkClientOwnership(client, grantedAccess);

        // the availability of the user
        final Optional<User> userOptional = userRepository.findByEmail(grantedAccess.getUsername());
        final User user = userOptional
                .orElseThrow(() -> new NotFoundException("No user: " + grantedAccess.getUsername()));
        checkUserAvailability(user);

        // authorities of the generated token
        final List<GrantedAuthority> authorities = new ArrayList<>(grantedAccess.getAuthorities());

        final UserDetails userDetails = UserDetailsImpl.builder()
                .username(user.getEmail())
                .authorities(authorities)
                .build();

        return jwtService.generateToken(userDetails, new HashMap<>(), new HashMap<>());
    }

    public String generateSingleCode(final String clientId, final String secret, final List<String> codes) {
        checkCodesPresence(codes);

        // client
        final Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
        final Client client = clientOptional
                .orElseThrow(() -> new NotFoundException("No client: " + clientId));
        checkClientCredentials(client, secret);

        final Set<GrantedAccess> access = new HashSet<>();

        for (final String code : codes) {
            // ownership of the client for the code
            final Optional<GrantedAccess> grantedAccessOptional = grantedAccessRepository.findByCode(code);
            final GrantedAccess grantedAccess = grantedAccessOptional
                    .orElseThrow(() -> new NotFoundException("No code: " + code));
            access.add(grantedAccess);

            checkClientOwnership(client, grantedAccess);
        }

        final List<String> users = access.stream()
                .map(GrantedAccess::getUsername)
                .collect(Collectors.toList());
        checkUserReference(users);

        final String username = users.get(0);

        final Set<Authority> authorities = access.stream()
                .map(GrantedAccess::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        // code
        final String code = UUID.randomUUID().toString();

        final GrantedAccess grantedAccess = new GrantedAccess()
                .setUsername(username)
                .setAuthorities(authorities)
                .setCode(code)
                .setClientId(clientId);

        grantedAccessRepository.save(grantedAccess);
        grantedAccessRepository.deleteAll(access);

        return code;
    }


    public Map<String, UserInfoDTO> findUsers(final String clientId, final String secret, final List<String> usernames) {
        // client
        final Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
        final Client client = clientOptional
                .orElseThrow(() -> new NotFoundException("No client: " + clientId));
        checkClientCredentials(client, secret);

        final Map<String, UserInfoDTO> userInfos = new HashMap<>();

        for (String username : usernames) {
            grantedAccessRepository.findByClientIdAndUsername(clientId, username).ifPresent(grantedAccess -> {
                val authorities = grantedAccess.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                userInfos.put(username, userInfoService.getUserInfo(authorities, username));
            });
        }

        return userInfos;
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

    private void checkCodesPresence(final List<String> codes) {
        if (codes.isEmpty()) {
            throw new UnauthorizedException("No codes");
        }
    }

    private void checkClientCredentials(final Client client, final String secret) {
        if (!passwordEncoder.matches(secret, client.getSecret())) {
            throw new UnauthorizedException("Bad client credentials");
        }
    }

    private void checkClientOwnership(final Client client, final GrantedAccess grantedAccess) {
        if (!grantedAccess.getClientId().equals(client.getClientId())) {
            throw new UnauthorizedException("Client can not access the code: " + grantedAccess.getCode());
        }
    }

    private void checkUserReference(final List<String> users) {
        if (users.size() > 1) {
            final String user = users.get(0);

            final long count = users.stream().filter(email -> !email.equals(user)).count();
            if (count != 0) {
                throw new UnauthorizedException("Codes do not reference the same user");
            }
        }
    }

    private void checkUserAvailability(final User user) {
        if (!user.isActive()) {
            throw new UnauthorizedException("User is not active");
        }
    }
}
