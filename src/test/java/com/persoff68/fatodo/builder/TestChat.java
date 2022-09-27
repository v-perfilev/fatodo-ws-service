package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.Chat;
import com.persoff68.fatodo.model.event.ChatMember;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestChat extends Chat {

    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestChat(UUID id, String title, List<ChatMember> members) {
        super();
        super.setId(id);
        super.setTitle(title);
        super.setMembers(members);
    }

    public static TestChatBuilder defaultBuilder() {
        return TestChat.builder()
                .id(UUID.randomUUID())
                .title(DEFAULT_VALUE)
                .members(Collections.emptyList());
    }

    public Chat toParent() {
        Chat chat = new Chat();
        chat.setId(getId());
        chat.setTitle(getTitle());
        chat.setMembers(getMembers());
        return chat;
    }

}
