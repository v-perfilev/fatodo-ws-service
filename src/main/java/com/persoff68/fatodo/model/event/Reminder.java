package com.persoff68.fatodo.model.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Reminder {

    private UUID parentId;

    private UUID targetId;

    private ReminderThreadType type;

    private Periodicity periodicity;

    private Date date;

    public enum ReminderThreadType {
        ITEM
    }

    public enum Periodicity {
        ONCE,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

}
