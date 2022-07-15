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
public class ChatMessage extends AbstractAuditingModel implements Serializable {
    @Serial
    private static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private UUID chatId;
    private UUID userId;
    private String text;
    private ChatMessage forwardedMessage;

    @JsonProperty("isDeleted")
    private boolean isDeleted;
    @JsonProperty("isEvent")
    private boolean isEvent;

    private List<ChatStatus> statuses;
    private List<ChatReaction> reactions;

}
