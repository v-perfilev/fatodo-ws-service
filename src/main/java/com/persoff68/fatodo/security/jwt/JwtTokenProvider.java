package com.persoff68.fatodo.security.jwt;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.config.constant.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final String USERNAME_KEY = "username";
    private final String AUTHORITY_KEY = "authorities";

    private final AppProperties appProperties;

    public String getUserIdFromJwt(String token) {
        return getClaimsFromJwt(token).getSubject();
    }

    public UsernamePasswordAuthenticationToken getAuthenticationFromJwt(String jwt) {
        Claims claims = getClaimsFromJwt(jwt);
        String username = claims.get(USERNAME_KEY).toString();
        List<? extends GrantedAuthority> authorityList =
                Arrays.stream(claims.get(AUTHORITY_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User user = new User(username, "", authorityList);
        return new UsernamePasswordAuthenticationToken(user, jwt, authorityList);
    }

    public String createUserJwt(String id, User user) {
        long tokenExpirationSec = appProperties.getAuth().getTokenExpirationSec();

        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date nowDate = new Date();
        Date expirationDate = new Date(nowDate.getTime()
                + TimeUnit.SECONDS.toMillis(tokenExpirationSec));

        JwtParams params = JwtParams.builder()
                .id(id)
                .username(user.getUsername())
                .authorities(authorities)
                .nowDate(nowDate)
                .expirationDate(expirationDate)
                .build();

        return buildJwt(params);
    }

    public String createSystemJwt() {
        Date nowDate = new Date();
        Date expirationDate = new Date(nowDate.getTime()
                + TimeUnit.SECONDS.toMillis(AppConstants.SYSTEM_TOKEN_EXPIRATION_SEC));

        JwtParams params = JwtParams.builder()
                .id("0")
                .username(AppConstants.SYSTEM_USERNAME)
                .authorities(AppConstants.SYSTEM_AUTHORITY)
                .nowDate(nowDate)
                .expirationDate(expirationDate)
                .build();

        return buildJwt(params);
    }

    public boolean validateJwt(String jwt) {
        try {
            String tokenSecret = appProperties.getAuth().getTokenSecret();
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private Claims getClaimsFromJwt(String jwt) {
        String tokenSecret = appProperties.getAuth().getTokenSecret();
        return Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(jwt)
                .getBody();
    }

    private String buildJwt(JwtParams params) {
        String tokenSecret = appProperties.getAuth().getTokenSecret();
        return Jwts.builder()
                .setSubject(params.getId())
                .claim(USERNAME_KEY, params.getUsername())
                .claim(AUTHORITY_KEY, params.getAuthorities())
                .setIssuedAt(params.getNowDate())
                .setExpiration(params.getExpirationDate())
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    @Getter
    @Builder
    private static class JwtParams {
        private String id;
        private String username;
        private String authorities;
        private Date nowDate;
        private Date expirationDate;
    }

}
