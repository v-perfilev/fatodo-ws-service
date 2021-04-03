package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.ChatStatuses;
import com.persoff68.fatodo.model.WsChatStatusesEvent;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestWsChatStatusesEvent extends WsChatStatusesEvent {

    @Builder
    TestWsChatStatusesEvent(List<UUID> userIds, ChatStatuses statuses) {
        super();
        super.setUserIds(userIds);
        super.setStatuses(statuses);
    }

    public static TestWsChatStatusesEventBuilder defaultBuilder() {
        return TestWsChatStatusesEvent.builder()
                .userIds(Collections.singletonList(UUID.randomUUID()))
                .statuses(new ChatStatuses());
    }

    public WsChatStatusesEvent toParent() {
        WsChatStatusesEvent event = new WsChatStatusesEvent();
        event.setUserIds(getUserIds());
        event.setStatuses(getStatuses());
        return event;
    }
}
