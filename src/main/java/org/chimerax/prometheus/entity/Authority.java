package org.chimerax.prometheus.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 10:03 AM
 */

public enum Authority implements GrantedAuthority {

    USER(Scope.USER),
    READ_PROFILE(Scope.PROFILE),
    READ_CONTACT(Scope.CONTACT),
    READ_CONTACTS(Scope.CONTACTS),
    READ_BILLING(Scope.BILLING),
    READ_FILES(Scope.FILES),
    WRITE_FILES(Scope.FILES),
    ;

    private Scope scope;

    Authority(Scope scope) {
        this.scope = scope;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

    public static Set<Authority> getForScope(final Scope scope) {
        return Stream.of(values())
                .filter(authority -> authority.isParent(scope))
                .collect(Collectors.toSet());
    }

    private boolean isParent(final Scope scope) {
        return this.scope == scope;
    }
}

