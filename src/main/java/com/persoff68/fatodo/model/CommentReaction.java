package com.persoff68.fatodo.model;

import com.persoff68.fatodo.model.constants.CommentReactionType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CommentReaction {

    private UUID commentId;

    private UUID userId;

    private CommentReactionType type;

    private Date timestamp;

}
