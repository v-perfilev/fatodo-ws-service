package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Request;
import com.persoff68.fatodo.model.constants.WsContactDestination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final WsService wsService;

    public void handleRequestIncomingEvent(List<UUID> userIdList, Request request) {
        String destination = WsContactDestination.CONTACT_REQUEST_INCOMING.getValue();
        wsService.sendMessage(userIdList, destination, request);
    }

    public void handleRequestOutcomingEvent(List<UUID> userIdList, Request request) {
        String destination = WsContactDestination.CONTACT_REQUEST_OUTCOMING.getValue();
        wsService.sendMessage(userIdList, destination, request);
    }

    public void handleAcceptIncomingEvent(List<UUID> userIdList, Request request) {
        String destination = WsContactDestination.CONTACT_ACCEPT_INCOMING.getValue();
        wsService.sendMessage(userIdList, destination, request);
    }

    public void handleAcceptOutcomingEvent(List<UUID> userIdList, Request request) {
        String destination = WsContactDestination.CONTACT_ACCEPT_OUTCOMING.getValue();
        wsService.sendMessage(userIdList, destination, request);
    }

}
