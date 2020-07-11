package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.chimerax.common.exception.UnauthorizedException;
import org.chimerax.common.security.jwt.UserDetailsImpl;
import org.chimerax.prometheus.entity.Authority;
import org.chimerax.prometheus.entity.User;
import org.chimerax.prometheus.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 9:04 PM
 */

@Service
@AllArgsConstructor
@Log
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private static final List<GrantedAuthority> AUTHORITIES = Collections.singletonList(Authority.USER);

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByEmail(username);
        final User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(username));
        checkUserAvailability(user);
        return UserDetailsImpl.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(AUTHORITIES)
                .build();
    }

    private void checkUserAvailability(final User user) {
        if (!user.isActive()) {
            throw new UnauthorizedException("User is not active");
        }
    }
}
