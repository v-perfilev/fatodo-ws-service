package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.Chat;
import com.persoff68.fatodo.model.WsChatEvent;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestWsChatEvent extends WsChatEvent {

    @Builder
    TestWsChatEvent(List<UUID> userIds, Chat chat) {
        super();
        super.setUserIds(userIds);
        super.setChat(chat);
    }

    public static TestWsChatEventBuilder defaultBuilder() {
        return TestWsChatEvent.builder()
                .userIds(Collections.singletonList(UUID.randomUUID()))
                .chat(new Chat());
    }

    public WsChatEvent toParent() {
        WsChatEvent event = new WsChatEvent();
        event.setUserIds(getUserIds());
        event.setChat(getChat());
        return event;
    }

}
