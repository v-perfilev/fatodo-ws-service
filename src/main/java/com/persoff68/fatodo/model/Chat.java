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
public class Chat extends AbstractAuditingModel implements Serializable {
    @Serial
    private static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private String title;
    @JsonProperty("isDirect")
    private boolean isDirect;

    private List<UUID> members;
    ChatMessage lastMessage;

}
