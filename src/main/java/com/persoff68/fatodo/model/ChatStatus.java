package com.persoff68.fatodo.model;

import com.persoff68.fatodo.model.constants.ChatStatusType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class ChatStatus implements Serializable {

    private UUID messageId;
    private UUID userId;
    private ChatStatusType type;
    private Date timestamp;

}
