package com.persoff68.fatodo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Chat extends AbstractAuditingModel {

    private String title;
    @JsonProperty("isDirect")
    private boolean isDirect;

    private List<UUID> members;
    ChatMessage lastMessage;

}
