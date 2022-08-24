package com.persoff68.fatodo.model;

import com.persoff68.fatodo.model.constant.WsEventType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class WsEvent {

    private List<UUID> userIds;

    private WsEventType type;

    private String payload;

    private UUID userId;

    private Date date;

}
