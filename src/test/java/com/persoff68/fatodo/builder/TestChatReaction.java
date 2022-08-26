package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.ChatReaction;
import lombok.Builder;

import java.util.UUID;

public class TestChatReaction extends ChatReaction {

    @Builder
    TestChatReaction(UUID chatId, UUID userId) {
        super();
        super.setChatId(chatId);
        super.setUserId(userId);
    }

    public static TestChatReactionBuilder defaultBuilder() {
        return TestChatReaction.builder()
                .chatId(UUID.randomUUID())
                .userId(UUID.randomUUID());
    }

    public ChatReaction toParent() {
        ChatReaction reaction = new ChatReaction();
        reaction.setChatId(getChatId());
        reaction.setUserId(getUserId());
        return reaction;
    }

}
