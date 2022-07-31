package com.persoff68.fatodo.model.constants;

public enum WsClearDestination {
    CLEAR("/clear");

    private final String value;

    WsClearDestination(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
