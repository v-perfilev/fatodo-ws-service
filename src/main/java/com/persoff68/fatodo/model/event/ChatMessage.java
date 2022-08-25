package com.persoff68.fatodo.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.persoff68.fatodo.model.AbstractAuditingModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends AbstractAuditingModel {

    private UUID chatId;

    private UUID userId;

    private String text;

    private ChatMessage reference;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @JsonProperty("isEvent")
    private boolean isEvent;

    private Set<ChatStatus> statuses;

    private Set<ChatReaction> reactions;

}
