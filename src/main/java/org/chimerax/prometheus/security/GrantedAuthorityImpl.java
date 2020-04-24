package org.chimerax.prometheus.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 9:20 PM
 */
@Getter
@AllArgsConstructor
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

}
