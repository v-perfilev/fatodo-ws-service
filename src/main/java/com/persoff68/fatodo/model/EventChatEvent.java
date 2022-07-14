package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EventChatEvent implements Serializable {

    private UUID userId;

    private UUID chatId;

    private UUID messageId;

    private String reaction;

    private List<UUID> userIds;

}
