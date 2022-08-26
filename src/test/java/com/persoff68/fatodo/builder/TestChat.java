package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.Chat;
import lombok.Builder;

import java.util.UUID;

public class TestChat extends Chat {

    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestChat(UUID id, String title) {
        super();
        super.setId(id);
        super.setTitle(title);
    }

    public static TestChatBuilder defaultBuilder() {
        return TestChat.builder()
                .id(UUID.randomUUID())
                .title(DEFAULT_VALUE);
    }

    public Chat toParent() {
        Chat chat = new Chat();
        chat.setId(getId());
        chat.setTitle(getTitle());
        return chat;
    }

}
