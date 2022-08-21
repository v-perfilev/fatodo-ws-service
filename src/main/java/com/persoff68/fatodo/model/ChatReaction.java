package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ChatReaction {

    private UUID chatId;

    private UUID messageId;

    private UUID userId;

    private ChatReactionType type;

    private Date timestamp;

    public enum ChatReactionType {
        LIKE,
        DISLIKE,
        NONE
    }

}
