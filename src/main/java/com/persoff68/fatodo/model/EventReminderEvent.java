package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class EventReminderEvent {

    private UUID groupId;

    private UUID itemId;

}
