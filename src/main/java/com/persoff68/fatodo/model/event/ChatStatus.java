package com.persoff68.fatodo.model.event;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ChatStatus {

    private UUID chatId;

    private UUID messageId;

    private UUID userId;

    private StatusType type;

    private Date date;

    public enum StatusType {
        READ
    }

}
