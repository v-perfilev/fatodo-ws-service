package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.WsEventWithUsers;
import com.persoff68.fatodo.model.constant.WsEventType;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestWsEventWithUsers extends WsEventWithUsers {

    @Builder
    TestWsEventWithUsers(List<UUID> userIds, WsEventType type, String payload) {
        super();
        super.setUserIds(userIds);
        super.setType(type);
        super.setPayload(payload);
    }

    public static TestWsEventWithUsersBuilder defaultBuilder() {
        return TestWsEventWithUsers.builder()
                .userIds(Collections.singletonList(UUID.randomUUID()));
    }

    public WsEventWithUsers toParent() {
        WsEventWithUsers event = new WsEventWithUsers();
        event.setUserIds(getUserIds());
        event.setType(getType());
        event.setPayload(getPayload());
        return event;
    }

}
