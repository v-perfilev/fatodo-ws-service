package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ChatStatus {

    private UUID chatId;

    private UUID messageId;

    private UUID userId;

    private ChatStatusType type;

    private Date date;

    public enum ChatStatusType {
        READ
    }

}
