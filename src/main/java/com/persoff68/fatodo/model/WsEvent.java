package com.persoff68.fatodo.model;

import com.persoff68.fatodo.model.constant.WsEventType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class WsEvent {

    private List<UUID> userIds;

    private WsEventType type;

    private Object payload;

}
