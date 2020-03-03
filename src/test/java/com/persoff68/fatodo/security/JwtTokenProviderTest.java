package com.persoff68.fatodo.security;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    JwtTokenProvider jwtTokenProvider;
    AppProperties appProperties;
    AppProperties.Auth auth;


    @BeforeEach
    void setup() {
        appProperties = Mockito.mock(AppProperties.class);
        auth = Mockito.mock(AppProperties.Auth.class);
        Mockito.when(appProperties.getAuth()).thenReturn(auth);
        Mockito.when(auth.getTokenSecret()).thenReturn("testTokenSecretTestTokenSecretTestTokenSecretTestTokenSecret");
        jwtTokenProvider = new JwtTokenProvider(appProperties);
    }

    @Test
    void testGetAuthenticationFromJwt() {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJ0ZXN0X3VzZXIiLCJhdXRob3JpdGllcyI6IlJPTEVfVEVTVCIsImlhdCI6MTU4MzI2ODYxMSwiZXhwIjoyNTgzMjY4NjcxfQ._gQPODOrXvpmc3WfHAli3kgbcm7mwu7SmmJAVqin8CK41v475Teeh4gUgsH-lTJqLNQCacBHmpBxPvloHEWFCw";
        List<? extends GrantedAuthority> authorityList =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEST"));
        User user = new User("test_user", "", authorityList);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, jwt, authorityList);
        Authentication resultAuthentication = jwtTokenProvider.getAuthenticationFromJwt(jwt);
        assertThat(resultAuthentication).isEqualTo(authentication);
    }

    @Test
    void testCreateSystemJwtAndGetUserIdFromJwt() {
        String jwt = jwtTokenProvider.createSystemJwt();
        String id = jwtTokenProvider.getUserIdFromJwt(jwt);
        assertThat(id).isEqualTo("0");
    }

    @Test
    void testCreateUserJwtAndValidateJwt() {
        Mockito.when(auth.getTokenExpirationSec()).thenReturn(100L);
        User user = new User("test_user", "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEST")));
        String jwt = jwtTokenProvider.createUserJwt("test_id", user);
        boolean isValid = jwtTokenProvider.validateJwt(jwt);
        assertThat(isValid).isTrue();
    }

}
