package com.persoff68.fatodo.service;

import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.AbstractModel;
import com.persoff68.fatodo.model.UserInfo;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.WsEventWithUsers;
import com.persoff68.fatodo.model.constants.WsDestination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final WsService wsService;
    private final FirebaseService firebaseService;
    private final UserServiceClient userServiceClient;

    public void handleEvent(WsEventWithUsers event) {
        List<UserInfo> userList = userServiceClient.getAllUserInfoByIds(event.getUserIds());
        if (event.getType().isState()) {
            handleStateEvent(userList, event);
        }
        if (event.getType().isEvent()) {
            handleStoryEvent(userList, event);
        }
        if (event.getType().isPush()) {
            handlePushEvent(userList, event);
        }
    }

    private void handleStateEvent(List<UserInfo> userList, WsEventWithUsers eventWithUsers) {
        List<String> usernameList = userList.stream().map(UserInfo::getUsername).toList();
        List<String> activeUsernameList = wsService.filterSubscribedUsers(usernameList, WsDestination.STATE.getValue());
        WsEvent event = WsEvent.of(eventWithUsers);
        wsService.sendMessages(activeUsernameList, WsDestination.STATE.getValue(), event);
    }

    private void handleStoryEvent(List<UserInfo> userList, WsEventWithUsers eventWithUsers) {
        List<String> usernameList = userList.stream().map(UserInfo::getUsername).toList();
        List<String> activeUsernameList = wsService.filterSubscribedUsers(usernameList, WsDestination.EVENT.getValue());
        WsEvent event = WsEvent.of(eventWithUsers);
        wsService.sendMessages(activeUsernameList, WsDestination.EVENT.getValue(), event);
    }

    private void handlePushEvent(List<UserInfo> userList, WsEventWithUsers eventWithUsers) {
        List<String> usernameList = userList.stream().map(UserInfo::getUsername).toList();
        List<String> activeUsernameList = wsService.filterSubscribedUsers(usernameList, WsDestination.PUSH.getValue());
        List<UUID> inactiveUserIdList = userList.stream()
                .filter(user -> !activeUsernameList.contains(user.getUsername()))
                .map(AbstractModel::getId)
                .toList();
        WsEvent event = WsEvent.of(eventWithUsers);
        wsService.sendMessages(activeUsernameList, WsDestination.PUSH.getValue(), event);
        firebaseService.sendMessages(inactiveUserIdList, event);
    }

}
