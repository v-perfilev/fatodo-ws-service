package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.ChatReactions;
import com.persoff68.fatodo.model.WsChatReactionsEvent;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestWsChatReactionsEvent extends WsChatReactionsEvent {

    @Builder
    TestWsChatReactionsEvent(List<UUID> userIds, ChatReactions reactions) {
        super();
        super.setUserIds(userIds);
        super.setReactions(reactions);
    }

    public static TestWsChatReactionsEventBuilder defaultBuilder() {
        return TestWsChatReactionsEvent.builder()
                .userIds(Collections.singletonList(UUID.randomUUID()))
                .reactions(new ChatReactions());
    }

}
