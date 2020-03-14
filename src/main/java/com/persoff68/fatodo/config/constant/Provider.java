package com.persoff68.fatodo.config.constant;

import lombok.Getter;

import java.util.Arrays;

public enum Provider {
    LOCAL("LOCAL"),
    GOOGLE("GOOGLE"),
    FACEBOOK("FACEBOOK");

    @Getter
    private String value;

    Provider(String value) {
        this.value = value;
    }

    public boolean contains(String value) {
        return Arrays.stream(Provider.values()).anyMatch(a -> a.getValue().equals(value));
    }
}
