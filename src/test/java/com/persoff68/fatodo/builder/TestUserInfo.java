package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.UserInfo;
import lombok.Builder;

import java.util.UUID;

public class TestUserInfo extends UserInfo {
    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestUserInfo(UUID id, String username) {
        super();
        super.setId(id);
        super.setUsername(username);
    }

    public static TestUserInfoBuilder defaultBuilder() {
        return TestUserInfo.builder()
                .id(UUID.randomUUID())
                .username(DEFAULT_VALUE);
    }

    public UserInfo toParent() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(getId());
        userInfo.setUsername(getUsername());
        return userInfo;
    }

}
