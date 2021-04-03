package com.persoff68.fatodo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WsService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    public void sendMessageToOneUser(UUID userId, String destination, Object payload) {
        List<String> usernameList = userService.getAllUsernamesByIds(Collections.singletonList(userId));
        usernameList.forEach(username -> messagingTemplate.convertAndSendToUser(username, destination, payload));
    }

    public void sendMessageToManyUsers(List<UUID> userIdList, String destination, Object payload) {
        List<String> usernameList = userService.getAllUsernamesByIds(userIdList);
        usernameList.forEach(username -> messagingTemplate.convertAndSendToUser(username, destination, payload));
    }

}
