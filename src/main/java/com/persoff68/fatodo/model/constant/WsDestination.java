package com.persoff68.fatodo.model.constant;

import com.persoff68.fatodo.config.constant.AppConstants;

public enum WsDestination {
    ROOT(AppConstants.WS_DESTINATION_PREFIX + "root");

    private final String value;

    WsDestination(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
