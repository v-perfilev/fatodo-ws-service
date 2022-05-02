package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.WsEvent;
import lombok.Builder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestWsEvent<T extends Serializable> extends WsEvent<T> {

    @Builder
    TestWsEvent(List<UUID> userIds, T content) {
        super();
        super.setUserIds(userIds);
        super.setContent(content);
    }

    public static <Z extends Serializable> TestWsEventBuilder<Z> defaultBuilder() {
        return TestWsEvent.<Z>builder()
                .userIds(Collections.singletonList(UUID.randomUUID()));
    }

    public WsEvent<T> toParent() {
        WsEvent<T> event = new WsEvent<>();
        event.setUserIds(getUserIds());
        event.setContent(getContent());
        return event;
    }

}
