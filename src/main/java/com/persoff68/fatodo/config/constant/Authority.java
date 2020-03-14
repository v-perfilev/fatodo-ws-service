package com.persoff68.fatodo.config.constant;

import lombok.Getter;

import java.util.Arrays;

public enum Authority {
    SYSTEM(),
    ADMIN(),
    USER();

    @Getter
    private String value;

    Authority() {
        this.value = "ROLE_" + this.name();
    }

    public boolean contains(String value) {
        return Arrays.stream(Authority.values()).anyMatch(a -> a.getValue().equals(value));
    }

}
