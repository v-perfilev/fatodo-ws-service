package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.Reminder;
import lombok.Builder;

import java.util.UUID;

public class TestReminder extends Reminder {

    @Builder
    TestReminder(UUID parentId, UUID targetId) {
        super();
        super.setParentId(parentId);
        super.setTargetId(targetId);
    }

    public static TestReminderBuilder defaultBuilder() {
        return TestReminder.builder()
                .parentId(UUID.randomUUID())
                .targetId(UUID.randomUUID());
    }

    public Reminder toParent() {
        Reminder reminder = new Reminder();
        reminder.setParentId(getParentId());
        reminder.setTargetId(getTargetId());
        return reminder;
    }

}
