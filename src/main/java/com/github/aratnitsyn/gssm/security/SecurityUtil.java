package com.github.aratnitsyn.gssm.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

public class SecurityUtil {
    private SecurityUtil() {

    }

    public static Optional<String> getCurrentUserLogin() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(externalPrincipal(securityContext.getAuthentication()));
    }

    private static String externalPrincipal(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            final UserDetails principal = (UserDetails) authentication.getPrincipal();
            return principal.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return ((String) authentication.getPrincipal());
        }
        return null;
    }
}
