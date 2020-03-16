package com.persoff68.fatodo.config.constant;

import lombok.Getter;

import java.util.Arrays;

public enum Provider {
    LOCAL(Constants.LOCAL_VALUE),
    GOOGLE(Constants.GOOGLE_VALUE),
    FACEBOOK(Constants.FACEBOOK_VALUE);

    @Getter
    private String value;

    Provider(String value) {
        this.value = value;
    }

    public static boolean contains(String value) {
        return Arrays.stream(Provider.values()).anyMatch(a -> a.getValue().equals(value));
    }

    public static class Constants {
        public static final String LOCAL_VALUE = "LOCAL";
        public static final String GOOGLE_VALUE = "GOOGLE";
        public static final String FACEBOOK_VALUE = "FACEBOOK";
    }
}
