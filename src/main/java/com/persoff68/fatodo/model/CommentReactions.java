package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CommentReactions {

    private UUID targetId;

    private UUID commentId;

    private List<CommentReaction> reactions;

}
