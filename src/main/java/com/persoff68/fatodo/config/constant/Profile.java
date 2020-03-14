package com.persoff68.fatodo.config.constant;

import lombok.Getter;

public enum Profile {
    DEVELOPMENT("dev"),
    DOCKER("docker"),
    PRODUCTION("prod"),
    TEST("test");

    @Getter
    private String value;

    Profile(String value) {
        this.value = value;
    }
}
