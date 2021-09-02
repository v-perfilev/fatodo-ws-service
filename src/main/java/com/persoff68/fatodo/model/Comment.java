package com.persoff68.fatodo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends AbstractAuditingModel {
    private UUID threadId;
    private UUID targetId;
    private UUID parentId;
    private UUID userId;
    private String text;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    private List<CommentReaction> reactions;

    private List<Comment> children;
}
