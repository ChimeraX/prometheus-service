package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.common.exception.UnauthorizedException;
import org.chimerax.common.security.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 11:24 PM
 */

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTService jwtService;


    public String authenticate(final String username, final String password) {
        _authenticate(username, password);
        return _generateToken(username);
    }

    private void _authenticate(final String username, final String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final UsernameNotFoundException exception) {
            throw new UnauthorizedException("Wrong username");
        } catch (final BadCredentialsException exception) {
            throw new UnauthorizedException("Wrong password");
        }
    }

    private String _generateToken(final String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails, new HashMap<>(), new HashMap<>());
    }
}
