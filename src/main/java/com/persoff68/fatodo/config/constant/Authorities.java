package com.persoff68.fatodo.config.constant;

import java.lang.reflect.Field;
import java.util.Arrays;

public interface Authorities {
    String SYSTEM = "ROLE_SYSTEM";
    String ADMIN = "ROLE_ADMIN";
    String USER = "ROLE_USER";

    static boolean contains(String authority) {
        return Arrays.stream(Authorities.class.getDeclaredFields())
                .map(Field::toString)
                .anyMatch(a -> a.equals(authority));
    }

    static String[] values() {
        return Arrays.stream(Authorities.class.getDeclaredFields())
                .map(Field::toString)
                .toArray(String[]::new);
    }

}
