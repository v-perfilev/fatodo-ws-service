package com.persoff68.fatodo.config.constant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProviderTest {

    @Test
    void testEnum() {
        assertThat(Provider.LOCAL.getValue()).isEqualTo(Provider.Constants.LOCAL_VALUE);
        assertThat(Provider.GOOGLE.getValue()).isEqualTo(Provider.Constants.GOOGLE_VALUE);
        assertThat(Provider.FACEBOOK.getValue()).isEqualTo(Provider.Constants.FACEBOOK_VALUE);
    }

    @Test
    void testContains() {
        boolean isTrue = Provider.contains("LOCAL");
        assertThat(isTrue).isTrue();
        boolean isFalse = Provider.contains("PROVIDER_NOT_EXISTS");
        assertThat(isFalse).isFalse();
    }

}
