package com.persoff68.fatodo.config.constant;

import java.util.Arrays;

public enum Language {
    DEFAULT(Constants.EN_VALUE),
    EN(Constants.EN_VALUE),
    RU(Constants.RU_VALUE);

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String value) {
        return Arrays.stream(Provider.values()).anyMatch(a -> a.getValue().equals(value));
    }

    private static class Constants {
        public static final String EN_VALUE = "en";
        public static final String RU_VALUE = "ru";

        private Constants() {
        }
    }
}
