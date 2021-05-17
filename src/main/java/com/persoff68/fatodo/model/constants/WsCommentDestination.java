package com.persoff68.fatodo.model.constants;

public enum WsCommentDestination {
    COMMENT_NEW("/comment/new/"),
    COMMENT_UPDATE("/comment/update/"),
    COMMENT_REACTION("/comment/reaction/");

    private final String value;

    WsCommentDestination(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
