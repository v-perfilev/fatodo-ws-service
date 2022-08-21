package com.persoff68.fatodo.model;

import com.persoff68.fatodo.model.constants.WsEventType;
import lombok.Data;

@Data
public class WsEvent {

    private WsEventType type;

    private Object payload;

    public static WsEvent of(WsEventWithUsers wsEventWithUsers) {
        WsEvent event = new WsEvent();
        event.type = wsEventWithUsers.getType();
        event.payload = wsEventWithUsers.getPayload();
        return event;
    }

}
