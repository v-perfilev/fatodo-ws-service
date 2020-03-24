package com.persoff68.fatodo.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUsername() {
        Authentication authentication = getCurrentAuthentication();
        String username = fetchUsernameFromAuthentication(authentication);
        return Optional.ofNullable(username);
    }

    public static Optional<String> getCurrentJwt() {
        Authentication authentication = getCurrentAuthentication();
        String username = fetchJwtFromAuthentication(authentication);
        return Optional.ofNullable(username);
    }

    private static String fetchUsernameFromAuthentication(Authentication authentication) {
        String username = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetails userDetails = (User) authentication.getPrincipal();
            username = userDetails.getUsername();
        }
        return username;
    }

    private static String fetchJwtFromAuthentication(Authentication authentication) {
        String jwt = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) authentication;
            jwt = (String) authenticationToken.getCredentials();
        }
        return jwt;
    }

    private static Authentication getCurrentAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }
}
