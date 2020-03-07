package com.persoff68.fatodo.config.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
public enum AuthorityType {

    SYSTEM("ROLE_SYSTEM"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    @Getter
    private String name;

    public static boolean contains(String name) {
        return Stream.of(AuthorityType.values()).map(AuthorityType::getName).anyMatch(n -> n.equals(name));
    }
}
