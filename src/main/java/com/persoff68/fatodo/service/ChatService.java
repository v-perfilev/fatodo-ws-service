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
public class ChatService {

    private final WsService wsService;

    public void handleChatNewEvent(List<UUID> userIdList, Chat chat) {
        String destination = WsChatDestination.CHAT_NEW.getValue();
        wsService.sendMessage(userIdList, destination, chat);
    }

    public void handleChatUpdateEvent(List<UUID> userIdList, Chat chat) {
        String destination = WsChatDestination.CHAT_UPDATE.getValue();
        wsService.sendMessage(userIdList, destination, chat);
    }

    public void handleChatDeleteEvent(List<UUID> userIdList, UUID chatId) {
        String destination = WsChatDestination.CHAT_DELETE.getValue();
        wsService.sendMessage(userIdList, destination, chatId);
    }

    public void handleChatLastMessageEvent(List<UUID> userIdList, Chat chat) {
        String destination = WsChatDestination.CHAT_LAST_MESSAGE.getValue();
        wsService.sendMessage(userIdList, destination, chat);
    }

    public void handleChatLastMessageUpdateEvent(List<UUID> userIdList, Chat chat) {
        String destination = WsChatDestination.CHAT_LAST_MESSAGE_UPDATE.getValue();
        wsService.sendMessage(userIdList, destination, chat);
    }

    public void handleMessageNewEvent(List<UUID> userIdList, ChatMessage message) {
        UUID chatId = message.getChatId();
        String destination = WsChatDestination.MESSAGE_NEW.getValue() + chatId;
        wsService.sendMessage(userIdList, destination, message);
    }

    public void handleMessageUpdateEvent(List<UUID> userIdList, ChatMessage message) {
        UUID chatId = message.getChatId();
        String destination = WsChatDestination.MESSAGE_UPDATE.getValue() + chatId;
        wsService.sendMessage(userIdList, destination, message);
    }

    public void handleStatusesEvent(List<UUID> userIdList, ChatStatuses statuses) {
        UUID chatId = statuses.getChatId();
        String destination = WsChatDestination.MESSAGE_STATUS.getValue() + chatId;
        wsService.sendMessage(userIdList, destination, statuses);
    }

    public void handleReactionsEvent(List<UUID> userIdList, ChatReactions reactions) {
        UUID chatId = reactions.getChatId();
        String destination = WsChatDestination.MESSAGE_REACTION.getValue() + chatId;
        wsService.sendMessage(userIdList, destination, reactions);
    }

}
