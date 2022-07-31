package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Event;
import com.persoff68.fatodo.model.constants.WsEventDestination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final WsService wsService;

    public void handleEvent(List<UUID> userIdList, Event event) {
        String destination = WsEventDestination.EVENT.getValue();
        wsService.sendMessage(userIdList, destination, event);
    }

}
