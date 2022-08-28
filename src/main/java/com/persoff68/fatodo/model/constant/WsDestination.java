package com.persoff68.fatodo.model.constant;

public enum WsDestination {
    ROOT("/topic/root");

    private final String value;

    WsDestination(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
