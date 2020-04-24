package org.chimerax.prometheus.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 1:03 PM
 */

@Component
@RequiredArgsConstructor
public class JWTServiceHelper {

    private static final String AUTHORITIES = "auth";

    @Value("${jwt.secret}")
    private String signingKey;

    public Claims extractAllClaims(final String token) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }

    public UserDetails extractUser(final String token) {
        final Claims claims = extractAllClaims(token);
        final String username = claims.getSubject();
        final List<String> authorityKeys = claims.get(AUTHORITIES, List.class);
        final List<GrantedAuthority> authorities = authorityKeys.stream()
                .map(GrantedAuthorityImpl::new)
                .collect(Collectors.toList());
        return UserDetailsImpl.builder()
                .username(username)
                .authorities(authorities)
                .build();
    }

    public String generateToken(final UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public String generateToken(final UserDetails userDetails, final Map<String, Object> extra) {
        final Instant now = Instant.now();
        final Instant expiration = now.plusSeconds(10 * 60 * 60);
        val authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .claim(AUTHORITIES, authorities)
                .addClaims(extra)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }
}
