package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.ReminderMeta;
import lombok.Builder;

import java.util.UUID;

public class TestReminderMeta extends ReminderMeta {

    @Builder
    TestReminderMeta(UUID groupId, UUID itemId) {
        super();
        super.setGroupId(groupId);
        super.setItemId(itemId);
    }

    public static TestReminderMetaBuilder defaultBuilder() {
        return TestReminderMeta.builder()
                .groupId(UUID.randomUUID())
                .itemId(UUID.randomUUID());
    }

    public ReminderMeta toParent() {
        ReminderMeta reminderMeta = new ReminderMeta();
        reminderMeta.setGroupId(getGroupId());
        reminderMeta.setItemId(getItemId());
        return reminderMeta;
    }

}
