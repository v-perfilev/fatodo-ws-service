package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Chat;
import com.persoff68.fatodo.model.ChatMessage;
import com.persoff68.fatodo.model.ChatReactions;
import com.persoff68.fatodo.model.ChatStatuses;
import com.persoff68.fatodo.model.constants.WsChatDestination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatEventService {

    private final WsService wsService;

    public void handleChatNewEvent(List<UUID> userIdList, Chat chat) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.CHAT_NEW.getValue(), chat);
    }

    public void handleChatUpdateEvent(List<UUID> userIdList, Chat chat) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.CHAT_UPDATE.getValue(), chat);
    }

    public void handleChatLastMessageEvent(List<UUID> userIdList, Chat chat) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.CHAT_LAST_MESSAGE.getValue(), chat);
    }

    public void handleChatLastMessageUpdateEvent(List<UUID> userIdList, Chat chat) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.CHAT_LAST_MESSAGE_UPDATE.getValue(), chat);
    }

    public void handleMessageNewEvent(List<UUID> userIdList, ChatMessage message) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.MESSAGE_NEW.getValue(), message);
    }

    public void handleMessageUpdateEvent(List<UUID> userIdList, ChatMessage message) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.MESSAGE_UPDATE.getValue(), message);
    }

    public void handleStatusesEvent(List<UUID> userIdList, ChatStatuses statuses) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.MESSAGE_STATUS.getValue(), statuses);
    }

    public void handleReactionsEvent(List<UUID> userIdList, ChatReactions reactions) {
        wsService.sendMessageToManyUsers(userIdList, WsChatDestination.MESSAGE_REACTION.getValue(), reactions);
    }

}
