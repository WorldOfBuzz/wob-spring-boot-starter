package com.ziffit.autoconfigure.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Checks whether the current user is authenticated.
     *
     * @return <code>true</code> if the user is authenticated, <code>false</code> otherwise
     */
    public static boolean isCurrentUserAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> SecurityConstants.ROLE_ANONYMOUS.equals(grantedAuthority.getAuthority()));
        } else {
            return false;
        }
    }

    /**
     * Converts a list of <code>String</code> roles into {@link GrantedAuthority} objects.
     *
     * @param roles A collection of security roles, in the format of <code>ROLE_ANONYMOUS</code>.
     * @return A collection of {@link SimpleGrantedAuthority} objects.
     */
    public static Collection<? extends GrantedAuthority> convertToGrantedAuthorities(List<String> roles) {
        if (roles == null) {
            return Collections.emptyList();
        } else {
            return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        }
    }
}
