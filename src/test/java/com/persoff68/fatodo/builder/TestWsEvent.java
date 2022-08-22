package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constant.WsEventType;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestWsEvent extends WsEvent {

    @Builder
    TestWsEvent(List<UUID> userIds, WsEventType type, String payload) {
        super();
        super.setUserIds(userIds);
        super.setType(type);
        super.setPayload(payload);
    }

    public static TestWsEventBuilder defaultBuilder() {
        return TestWsEvent.builder()
                .userIds(Collections.singletonList(UUID.randomUUID()));
    }

    public WsEvent toParent() {
        WsEvent event = new WsEvent();
        event.setUserIds(getUserIds());
        event.setType(getType());
        event.setPayload(getPayload());
        return event;
    }

}
