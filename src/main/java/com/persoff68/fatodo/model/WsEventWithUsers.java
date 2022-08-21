package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class WsEventWithUsers extends WsEvent {

    private List<UUID> userIds;

}
