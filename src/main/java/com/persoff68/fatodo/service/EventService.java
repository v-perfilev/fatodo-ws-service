package com.persoff68.fatodo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.client.UserSystemServiceClient;
import com.persoff68.fatodo.model.User;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constant.WsDestination;
import com.persoff68.fatodo.service.exception.ModelInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final WsService wsService;
    private final FirebaseService firebaseService;
    private final UserSystemServiceClient userSystemServiceClient;
    private final ObjectMapper objectMapper;

    public void handleEvent(WsEvent event) {
        List<User> userList = userSystemServiceClient.getAllUserDataByIds(event.getUserIds());
        List<String> usernameList = userList.stream().map(User::getUsername).toList();
        List<String> activeUsernameList = wsService.filterSubscribedUsers(usernameList);

        handleWsEvent(event, activeUsernameList);
        handleFirebaseEvent(event, userList, activeUsernameList);
    }

    private void handleWsEvent(WsEvent event, List<String> activeUsernameList) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            wsService.sendMessages(activeUsernameList, WsDestination.ROOT.getValue(), payload);
        } catch (JsonProcessingException e) {
            throw new ModelInvalidException();
        }
    }

    private void handleFirebaseEvent(WsEvent event, List<User> userList, List<String> activeUsernameList) {
        boolean isPushEvent = event.getType().isPushEvent();
        boolean hasInactiveUsers = activeUsernameList.size() != userList.size();

        if (isPushEvent && hasInactiveUsers) {
            List<User> inactiveUserList = userList.stream()
                    .filter(user -> !activeUsernameList.contains(user.getUsername()))
                    .filter(user -> !user.getId().equals(event.getUserId()))
                    .toList();
            firebaseService.sendMessages(inactiveUserList, event.getType(), event.getPayload());
        }
    }

}
