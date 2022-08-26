package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.ChatMessage;
import lombok.Builder;

import java.util.UUID;

public class TestChatMessage extends ChatMessage {

    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestChatMessage(UUID id, UUID chatId, String text, UUID userId) {
        super();
        super.setId(id);
        super.setChatId(chatId);
        super.setText(text);
        super.setUserId(userId);
    }

    public static TestChatMessageBuilder defaultBuilder() {
        return TestChatMessage.builder()
                .id(UUID.randomUUID())
                .chatId(UUID.randomUUID())
                .text(DEFAULT_VALUE)
                .userId(UUID.randomUUID());
    }

    public ChatMessage toParent() {
        ChatMessage message = new ChatMessage();
        message.setId(getId());
        message.setChatId(getChatId());
        message.setText(getText());
        message.setUserId(getUserId());
        return message;
    }

}
