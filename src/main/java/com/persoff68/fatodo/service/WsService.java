package com.persoff68.fatodo.service;

import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.config.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.internal.util.Strings;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WsService {

    private final SimpUserRegistry userRegistry;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserServiceClient userServiceClient;

    public void sendMessage(List<UUID> userIdList, String destination, Object payload) {
        List<String> usernameList = userServiceClient.getAllUsernamesByIds(userIdList);
        List<String> subscribedList = filterSubscribedUsers(usernameList, destination);
        subscribedList.forEach(username -> messagingTemplate.convertAndSendToUser(username, destination, payload));
    }

    private List<String> filterSubscribedUsers(List<String> usernameList, String destination) {
        return userRegistry.findSubscriptions(destinationMatcher(destination)).stream()
                .map(extractUsernameFromSubscription())
                .filter(Strings::isNotEmpty)
                .distinct()
                .filter(usernameList::contains)
                .toList();
    }

    private SimpSubscriptionMatcher destinationMatcher(String destination) {
        return subscription -> subscription.getDestination().equals(destination)
                || subscription.getDestination().equals(AppConstants.WS_USER_PREFIX + destination);
    }

    private Function<SimpSubscription, String> extractUsernameFromSubscription() {
        return subscription -> {
            try {
                return subscription.getSession().getUser().getName();
            } catch (NullPointerException e) {
                return "";
            }
        };
    }

}
