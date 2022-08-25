package com.persoff68.fatodo.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.persoff68.fatodo.model.constant.WsEventType;
import com.persoff68.fatodo.model.event.Chat;
import com.persoff68.fatodo.model.event.ChatMessage;
import com.persoff68.fatodo.model.event.ChatReaction;
import com.persoff68.fatodo.model.event.Comment;
import com.persoff68.fatodo.model.event.CommentReaction;
import com.persoff68.fatodo.model.event.ContactRequest;
import com.persoff68.fatodo.model.event.Item;
import com.persoff68.fatodo.model.event.ItemGroup;
import com.persoff68.fatodo.model.event.ItemGroupMember;
import com.persoff68.fatodo.model.event.Reminder;
import com.persoff68.fatodo.service.exception.MessagingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseService {

    private final FirebaseMessaging firebaseMessaging;
    private final JsonService jsonService;

    public void sendMessages(List<UUID> userIdList, WsEventType type, String payload) {
        FirebaseMessageData messageData = buildMessageData(type, payload);
        userIdList.forEach(userId -> send(userId, messageData));
    }

    private FirebaseMessageData buildMessageData(WsEventType type, String payload) {
        return switch (type) {
            case ITEM_CREATE -> buildItemCreateData(payload);
            case ITEM_GROUP_CREATE -> buildItemGroupCreateData(payload);
            case ITEM_MEMBER_ADD -> buildItemMemberAddData(payload);
            case CHAT_CREATE -> buildChatCreateData(payload);
            case CHAT_MESSAGE_CREATE -> buildChatMessageCreateData(payload);
            case CHAT_REACTION_INCOMING -> buildChatReactionIncomingData(payload);
            case CONTACT_REQUEST_INCOMING -> buildContactRequestIncomingData(payload);
            case CONTACT_ACCEPT_INCOMING -> buildContactAcceptIncomingData(payload);
            case COMMENT_CREATE -> buildCommentCreateData(payload);
            case COMMENT_REACTION_INCOMING -> buildCommentReactionIncoming(payload);
            case REMINDER -> buildReminderData(payload);
            default -> throw new MessagingException();
        };
    }

    private FirebaseMessageData buildItemCreateData(String payload) {
        Item item = jsonService.deserialize(payload, Item.class);

        return null;
    }

    private FirebaseMessageData buildItemGroupCreateData(String payload) {
        ItemGroup group = jsonService.deserialize(payload, ItemGroup.class);

        return null;
    }

    private FirebaseMessageData buildItemMemberAddData(String payload) {
        List<ItemGroupMember> memberList = jsonService.deserializeList(payload, ItemGroupMember.class);

        return null;
    }

    private FirebaseMessageData buildChatCreateData(String payload) {
        Chat chat = jsonService.deserialize(payload, Chat.class);

        return null;
    }

    private FirebaseMessageData buildChatMessageCreateData(String payload) {
        ChatMessage chatMessage = jsonService.deserialize(payload, ChatMessage.class);

        return null;
    }

    private FirebaseMessageData buildChatReactionIncomingData(String payload) {
        ChatReaction reaction = jsonService.deserialize(payload, ChatReaction.class);

        return null;
    }

    private FirebaseMessageData buildContactRequestIncomingData(String payload) {
        ContactRequest reaction = jsonService.deserialize(payload, ContactRequest.class);

        return null;
    }

    private FirebaseMessageData buildContactAcceptIncomingData(String payload) {
        ContactRequest reaction = jsonService.deserialize(payload, ContactRequest.class);

        return null;
    }

    private FirebaseMessageData buildCommentCreateData(String payload) {
        Comment comment = jsonService.deserialize(payload, Comment.class);

        return null;
    }

    private FirebaseMessageData buildCommentReactionIncoming(String payload) {
        CommentReaction reaction = jsonService.deserialize(payload, CommentReaction.class);

        return null;
    }

    private FirebaseMessageData buildReminderData(String payload) {
        Reminder reminder = jsonService.deserialize(payload, Reminder.class);

        return null;
    }

    private void send(UUID userId, FirebaseMessageData data) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(data.title)
                    .setBody(data.body)
                    .build();

            Message message = Message.builder()
                    .setTopic(userId.toString())
                    .setNotification(notification)
                    .putAllData(data.dataMap)
                    .build();

            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new MessagingException();
        }
    }

    @Data
    @AllArgsConstructor
    private static class FirebaseMessageData {
        private String title;
        private String body;
        private Map<String, String> dataMap;
    }

}
