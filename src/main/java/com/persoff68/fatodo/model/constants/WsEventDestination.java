package com.persoff68.fatodo.model.constants;

public enum WsEventDestination {
    EVENT("/event"),
    EVENT_DELETE("/event-delete");

    private final String value;

    WsEventDestination(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
