package com.persoff68.fatodo.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.persoff68.fatodo.model.AbstractAuditingModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends AbstractAuditingModel {

    private UUID parentId;

    private UUID targetId;

    private UUID userId;

    private String text;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    private Set<CommentReaction> reactions;
}
