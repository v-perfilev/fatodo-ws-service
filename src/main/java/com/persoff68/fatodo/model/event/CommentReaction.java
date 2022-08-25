package com.persoff68.fatodo.model.event;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CommentReaction {

    private UUID parentId;

    private UUID targetId;

    private UUID commentId;

    private UUID userId;

    private ReactionType type;

    private Date date;

    public enum ReactionType {
        LIKE,
        DISLIKE,
        NONE
    }

}
