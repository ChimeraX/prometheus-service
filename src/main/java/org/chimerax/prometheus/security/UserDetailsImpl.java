package org.chimerax.prometheus.security;

import lombok.Builder;
import lombok.Getter;
import org.chimerax.prometheus.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 25-Apr-20
 * Time: 12:03 AM
 */

@Builder
@Getter
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
