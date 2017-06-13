package com.ziffit.autoconfigure.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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
}
