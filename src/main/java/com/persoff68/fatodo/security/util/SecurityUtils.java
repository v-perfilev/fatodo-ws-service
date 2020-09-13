package com.persoff68.fatodo.security.util;

import com.persoff68.fatodo.security.details.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<String> getCurrentId() {
        Authentication authentication = getCurrentAuthentication();
        String id = fetchIdFromAuthentication(authentication);
        return Optional.ofNullable(id);
    }

    public static Optional<String> getCurrentUsername() {
        Authentication authentication = getCurrentAuthentication();
        String username = fetchUsernameFromAuthentication(authentication);
        return Optional.ofNullable(username);
    }

    public static Optional<Set<GrantedAuthority>> getCurrentAuthoritySet() {
        Authentication authentication = getCurrentAuthentication();
        Set<GrantedAuthority> authoritySet = fetchAuthoritiesFromAuthentication(authentication);
        return Optional.ofNullable(authoritySet);
    }


    public static Optional<String> getCurrentJwt() {
        Authentication authentication = getCurrentAuthentication();
        String username = fetchJwtFromAuthentication(authentication);
        return Optional.ofNullable(username);
    }

    private static String fetchIdFromAuthentication(Authentication authentication) {
        String id = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            id = userDetails.getId();
        }
        return id;
    }

    private static String fetchUsernameFromAuthentication(Authentication authentication) {
        String username = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }
        return username;
    }

    private static Set<GrantedAuthority> fetchAuthoritiesFromAuthentication(Authentication authentication) {
        Set<GrantedAuthority> authoritySet = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) authentication;
            authoritySet = new HashSet<>(authenticationToken.getAuthorities());
        }
        return authoritySet;
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
