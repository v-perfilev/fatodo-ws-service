package com.persoff68.fatodo.model;

import com.persoff68.fatodo.model.constants.ChatStatusType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ChatStatus {

    private UUID messageId;

    private UUID userId;

    private ChatStatusType type;

    private Date timestamp;

}
