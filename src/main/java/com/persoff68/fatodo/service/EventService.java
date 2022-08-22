package com.persoff68.fatodo.service;

import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.AbstractModel;
import com.persoff68.fatodo.model.UserInfo;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constant.WsDestination;
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

    public void handleEvent(WsEvent event) {
        List<UserInfo> userList = userServiceClient.getAllUserInfoByIds(event.getUserIds());
        List<String> usernameList = userList.stream().map(UserInfo::getUsername).toList();
        List<String> activeUsernameList = wsService.filterSubscribedUsers(usernameList);
        // TODO handle destination
        wsService.sendMessages(activeUsernameList, WsDestination.EVENT.getValue(), event);

        if (activeUsernameList.size() != userList.size() && event.getType().isPush()) {
            List<UUID> inactiveUserIdList = userList.stream()
                    .filter(user -> !activeUsernameList.contains(user.getUsername()))
                    .map(AbstractModel::getId)
                    .toList();
            firebaseService.sendMessages(inactiveUserIdList, event);
        }
    }


}
