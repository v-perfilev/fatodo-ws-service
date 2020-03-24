package com.persoff68.fatodo.config.constant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileTest {

    @Test
    void testEnum() {
        assertThat(Profile.DEVELOPMENT.getValue()).isEqualTo(Profile.Constants.DEVELOPMENT_VALUE);
        assertThat(Profile.DOCKER.getValue()).isEqualTo(Profile.Constants.DOCKER_VALUE);
        assertThat(Profile.DEVELOPMENT.getValue()).isEqualTo(Profile.Constants.DEVELOPMENT_VALUE);
        assertThat(Profile.TEST.getValue()).isEqualTo(Profile.Constants.TEST_VALUE);
    }

}
