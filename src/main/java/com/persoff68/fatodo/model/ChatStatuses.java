package com.persoff68.fatodo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class ChatStatuses implements Serializable {

    private UUID chatId;
    private UUID messageId;

    private List<ChatStatus> statuses;

}
