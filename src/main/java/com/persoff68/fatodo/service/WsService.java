package com.persoff68.fatodo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WsService {

    private final SimpUserRegistry userRegistry;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessages(List<String> usernameList, String destination, Object payload) {
        usernameList.forEach(username -> messagingTemplate.convertAndSendToUser(username, destination, payload));
    }

    public List<String> filterSubscribedUsers(List<String> usernameList) {
        return userRegistry.findSubscriptions(s -> true).stream()
                .map(extractUsernameFromSubscription())
                .filter(Objects::nonNull)
                .distinct()
                .filter(usernameList::contains)
                .toList();
    }

    private Function<SimpSubscription, String> extractUsernameFromSubscription() {
        return subscription -> {
            try {
                return subscription.getSession().getUser().getName();
            } catch (NullPointerException e) {
                return null;
            }
        };
    }

}
