package com.persoff68.fatodo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends AbstractAuditingModel {

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
