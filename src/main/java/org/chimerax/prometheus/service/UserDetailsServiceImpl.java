package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
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
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private static final List<GrantedAuthority> AUTHORITIES = Collections.singletonList(Authority.USER);

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> userOptional = userRepository.findByEmail(username);
        final User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        return UserDetailsImpl.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(AUTHORITIES)
                .build();
    }
}
