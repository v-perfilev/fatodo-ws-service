package com.persoff68.fatodo.model;

import com.persoff68.fatodo.config.constant.AppConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class WsChatMessageEvent implements Serializable {
    protected static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private List<UUID> userIds;
    private ChatMessage message;

}
