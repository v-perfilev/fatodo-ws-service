package com.persoff68.fatodo.model;

import com.persoff68.fatodo.config.constant.AppConstants;
import com.persoff68.fatodo.model.constants.CommentReactionType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class CommentReaction implements Serializable {
    protected static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private UUID commentId;
    private UUID userId;
    private CommentReactionType type;
    private Date timestamp;
}
