package com.persoff68.fatodo.model.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ReminderMeta {

    private UUID groupId;

    private UUID itemId;

}
