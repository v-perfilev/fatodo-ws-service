package com.persoff68.fatodo.config.constant;

import lombok.Getter;

import java.util.Arrays;

public enum AuthorityType {
    SYSTEM(Constants.SYSTEM_VALUE),
    ADMIN(Constants.ADMIN_VALUE),
    USER(Constants.USER_VALUE);

    @Getter
    private final String value;

    AuthorityType(String value) {
        this.value = value;
    }

    public static boolean contains(String value) {
        return Arrays.stream(AuthorityType.values()).anyMatch(a -> a.getValue().equals(value));
    }

    public static class Constants {
        public static final String SYSTEM_VALUE = "ROLE_SYSTEM";
        public static final String ADMIN_VALUE = "ROLE_ADMIN";
        public static final String USER_VALUE = "ROLE_USER";

        private Constants() {
        }
    }

}
