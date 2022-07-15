package com.persoff68.fatodo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.persoff68.fatodo.config.constant.AppConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends AbstractAuditingModel implements Serializable {
    @Serial
    private static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private UUID threadId;
    private UUID targetId;
    private UUID userId;
    private String text;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    private Comment reference;

    private List<CommentReaction> reactions;
}
