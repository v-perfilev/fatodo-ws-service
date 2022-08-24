package com.persoff68.fatodo.model.constant;

import lombok.Getter;

public enum WsEventType {

    WELCOME(),

    ITEM_GROUP_CREATE(true),
    ITEM_GROUP_UPDATE(),
    ITEM_GROUP_DELETE(),
    ITEM_CREATE(true),
    ITEM_UPDATE(),
    ITEM_UPDATE_STATUS(),
    ITEM_UPDATE_ARCHIVED(),
    ITEM_DELETE(),
    ITEM_MEMBER_ADD(true),
    ITEM_MEMBER_DELETE(),
    ITEM_MEMBER_LEAVE(),
    ITEM_MEMBER_ROLE(),

    CHAT_CREATE(true),
    CHAT_UPDATE(),
    CHAT_MEMBER_ADD(),
    CHAT_MEMBER_DELETE(),
    CHAT_MEMBER_LEAVE(),
    CHAT_MESSAGE_CREATE(true),
    CHAT_MESSAGE_UPDATE(),
    CHAT_REACTION(),
    CHAT_REACTION_INCOMING(true),
    CHAT_STATUS(),

    CONTACT_REQUEST_INCOMING(true),
    CONTACT_REQUEST_OUTCOMING(),
    CONTACT_ACCEPT_INCOMING(true),
    CONTACT_ACCEPT_OUTCOMING(),
    CONTACT_DELETE_INCOMING(),
    CONTACT_DELETE_OUTCOMING(),
    CONTACT_DELETE(),

    COMMENT_CREATE(true),
    COMMENT_UPDATE(),
    COMMENT_REACTION(),
    COMMENT_REACTION_INCOMING(true),

    REMINDER(true);

    @Getter
    private final Class<?> payloadClass;
    @Getter
    private final boolean push;

    WsEventType() {
        this.payloadClass = null;
        this.push = false;
    }

    WsEventType(boolean push) {
        this.payloadClass = null;
        this.push = push;
    }


}
