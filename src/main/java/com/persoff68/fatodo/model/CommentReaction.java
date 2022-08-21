package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CommentReaction {

    private UUID targetId;

    private UUID commentId;

    private UUID userId;

    private CommentReactionType type;

    private Date timestamp;

    public enum CommentReactionType {
        LIKE,
        DISLIKE,
        NONE
    }
}
