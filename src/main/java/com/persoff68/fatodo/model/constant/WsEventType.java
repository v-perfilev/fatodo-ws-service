package com.persoff68.fatodo.model.constant;

import lombok.Getter;

public enum WsEventType {

    WELCOME(null, false, true, false),

    ITEM_GROUP_CREATE(null, true, true, true),
    ITEM_GROUP_UPDATE(null, true, true, false),
    ITEM_GROUP_DELETE(null, true, false, false),
    ITEM_CREATE(null, true, true, true),
    ITEM_UPDATE(null, true, true, false),
    ITEM_DELETE(null, true, true, false),
    ITEM_MEMBER_ADD(null, true, true, true),
    ITEM_MEMBER_DELETE(null, true, true, false),
    ITEM_MEMBER_LEAVE(null, true, true, false),
    ITEM_MEMBER_ROLE(null, true, true, false),

    CHAT_CREATE(null, true, true, true),
    CHAT_UPDATE(null, true, true, false),
    CHAT_DELETE(null, true, false, true),
    CHAT_MEMBER_ADD(null, true, true, false),
    CHAT_MEMBER_DELETE(null, true, true, false),
    CHAT_MEMBER_LEAVE(null, true, true, false),
    CHAT_MESSAGE_CREATE(null, true, false, true),
    CHAT_MESSAGE_UPDATE(null, true, false, false),
    CHAT_REACTION(null, true, false, false),
    CHAT_REACTION_INCOMING(null, false, true, true),
    CHAT_STATUS(null, true, false, false),

    CONTACT_REQUEST_INCOMING(null, true, true, true),
    CONTACT_REQUEST_OUTCOMING(null, true, true, false),
    CONTACT_ACCEPT_INCOMING(null, true, true, true),
    CONTACT_ACCEPT_OUTCOMING(null, true, true, false),
    CONTACT_DELETE_INCOMING(null, true, false, false),
    CONTACT_DELETE_OUTCOMING(null, true, false, false),
    CONTACT_DELETE(null, true, false, false),

    COMMENT_CREATE(null, true, true, true),
    COMMENT_UPDATE(null, true, false, false),
    COMMENT_REACTION(null, true, false, false),
    COMMENT_REACTION_INCOMING(null, false, true, true),

    REMINDER(null, false, true, true);

    @Getter
    private final Class<?> payloadClass;
    @Getter
    private final boolean state;
    @Getter
    private final boolean event;
    @Getter
    private final boolean push;

    WsEventType(Class<?> payloadClass, boolean state, boolean event, boolean push) {
        this.payloadClass = payloadClass;
        this.state = state;
        this.event = event;
        this.push = push;
    }

}
