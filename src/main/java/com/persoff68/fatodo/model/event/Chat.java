package com.persoff68.fatodo.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.persoff68.fatodo.model.AbstractAuditingModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Chat extends AbstractAuditingModel {

    private String title;

    @JsonProperty("isDirect")
    private boolean isDirect;

    private List<ChatMember> members;

    ChatMessage lastMessage;

}
