package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.ClearEvent;
import com.persoff68.fatodo.model.constants.WsClearDestination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClearService {

    private final WsService wsService;

    public void handleClearEvent(List<UUID> userIdList, ClearEvent clearEvent) {
        String destination = WsClearDestination.CLEAR.getValue();
        wsService.sendMessage(userIdList, destination, clearEvent);
    }

}
