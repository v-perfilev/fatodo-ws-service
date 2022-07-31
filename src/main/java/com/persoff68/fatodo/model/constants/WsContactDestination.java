package com.persoff68.fatodo.model.constants;

public enum WsContactDestination {
    CONTACT_REQUEST_INCOMING("/contact/request-incoming"),
    CONTACT_REQUEST_OUTCOMING("/contact/request-outcoming"),
    CONTACT_ACCEPT_INCOMING("/contact/accept-incoming"),
    CONTACT_ACCEPT_OUTCOMING("/contact/accept-outcoing");

    private final String value;

    WsContactDestination(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
