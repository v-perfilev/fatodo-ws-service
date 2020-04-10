package com.persoff68.fatodo.config.constant;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorityTypeTest {

    @Test
    void testEnum() {
        assertThat(AuthorityType.ADMIN.getValue()).isEqualTo(AuthorityType.Constants.ADMIN_VALUE);
        assertThat(AuthorityType.SYSTEM.getValue()).isEqualTo(AuthorityType.Constants.SYSTEM_VALUE);
        assertThat(AuthorityType.USER.getValue()).isEqualTo(AuthorityType.Constants.USER_VALUE);
    }

    @Test
    void testContains() {
        boolean isTrue = AuthorityType.contains("ROLE_SYSTEM");
        assertThat(isTrue).isTrue();
        boolean isFalse = AuthorityType.contains("ROLE_NOT_EXISTS");
        assertThat(isFalse).isFalse();
    }

    @Test
    void testGetGrantedAuthority() {
        assertThat(AuthorityType.ADMIN.getGrantedAuthority())
                .isEqualTo(new SimpleGrantedAuthority(AuthorityType.Constants.ADMIN_VALUE));
    }

}
