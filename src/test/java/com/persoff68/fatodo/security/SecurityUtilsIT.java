package com.persoff68.fatodo.security;

import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.security.util.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SecurityUtils.class)
@ExtendWith(MockitoExtension.class)
public class SecurityUtilsIT {

    @Test
    @WithAnonymousUser
    void testGetCurrentUsername_ifAnonymous() {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUsername();
        assertThat(usernameOptional.isPresent()).isFalse();
    }

    @Test
    @WithCustomSecurityContext
    void testGetCurrentUsername_ifAuthorized() {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUsername();
        assertThat(usernameOptional.isPresent()).isTrue();
        assertThat(usernameOptional.get()).isEqualTo("test_username");
    }

    @Test
    @WithAnonymousUser
    void testGetCurrentJwt_ifAnonymous() {
        Optional<String> jwtOptional = SecurityUtils.getCurrentJwt();
        assertThat(jwtOptional.isPresent()).isFalse();
    }

    @Test
    @WithCustomSecurityContext
    void testGetCurrentJwt_ifAuthorized() {
        Optional<String> jwtOptional = SecurityUtils.getCurrentJwt();
        assertThat(jwtOptional.isPresent()).isTrue();
        assertThat(jwtOptional.get()).isEqualTo("test_jwt");
    }

}
