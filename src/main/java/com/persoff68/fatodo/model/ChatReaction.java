package com.persoff68.fatodo.model;

import com.persoff68.fatodo.model.constants.ChatReactionType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class ChatReaction implements Serializable {

    private UUID messageId;
    private UUID userId;
    private ChatReactionType type;
    private Date timestamp;

}
