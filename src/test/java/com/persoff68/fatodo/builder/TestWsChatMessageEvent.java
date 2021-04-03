package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.ChatMessage;
import com.persoff68.fatodo.model.WsChatMessageEvent;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestWsChatMessageEvent extends WsChatMessageEvent {

    @Builder
    TestWsChatMessageEvent(List<UUID> userIds, ChatMessage message) {
        super();
        super.setUserIds(userIds);
        super.setMessage(message);
    }

    public static TestWsChatMessageEventBuilder defaultBuilder() {
        return TestWsChatMessageEvent.builder()
                .userIds(Collections.singletonList(UUID.randomUUID()))
                .message(new ChatMessage());
    }

    public WsChatMessageEvent toParent() {
        WsChatMessageEvent event = new WsChatMessageEvent();
        event.setUserIds(getUserIds());
        event.setMessage(getMessage());
        return event;
    }
}
