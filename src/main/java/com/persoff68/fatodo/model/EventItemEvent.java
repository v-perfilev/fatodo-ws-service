package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EventItemEvent {

    private UUID userId;

    private UUID groupId;

    private UUID itemId;

    private List<UUID> userIds;

}
