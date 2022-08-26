package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.ItemGroupMember;
import lombok.Builder;

import java.util.UUID;

public class TestItemGroupMember extends ItemGroupMember {

    @Builder
    TestItemGroupMember(UUID groupId, UUID userId) {
        super();
        super.setGroupId(groupId);
        super.setUserId(userId);
    }

    public static TestItemGroupMemberBuilder defaultBuilder() {
        return TestItemGroupMember.builder()
                .groupId(UUID.randomUUID())
                .userId(UUID.randomUUID());
    }

    public ItemGroupMember toParent() {
        ItemGroupMember member = new ItemGroupMember();
        member.setGroupId(getGroupId());
        member.setUserId(getUserId());
        return member;
    }

}
