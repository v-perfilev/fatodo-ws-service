package com.persoff68.fatodo.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.client.configuration.ItemServiceClient;
import com.persoff68.fatodo.model.ItemInfo;
import com.persoff68.fatodo.model.UserInfo;
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
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirebaseService {

    private final static String ITEM_ID = "itemId";
    private final static String GROUP_ID = "groupId";
    private final static String CHAT_ID = "chatId";
    private final static String USER_ID = "userId";
    private final static String TARGET_ID = "targetId";

    private final FirebaseMessaging firebaseMessaging;
    private final JsonService jsonService;
    private final MessageSource messageSource;
    private final UserServiceClient userServiceClient;
    private final ItemServiceClient itemServiceClient;

    public void sendMessages(List<UserInfo> userList, WsEventType type, String payload) {
        List<Locale> localeList = userList.stream()
                .map(UserInfo::getLanguage)
                .distinct()
                .map(Locale::forLanguageTag)
                .toList();

        Map<Locale, FirebaseMessageData> messageDataMap = buildMessageDataMap(type, payload, localeList);

        userList.forEach(user -> {
            String language = user.getLanguage();
            Locale locale = Locale.forLanguageTag(language);
            FirebaseMessageData messageData = messageDataMap.get(locale);
            send(user.getId(), messageData);
        });
    }

    private Map<Locale, FirebaseMessageData> buildMessageDataMap(WsEventType type,
                                                                 String payload,
                                                                 List<Locale> localeList) {
        return switch (type) {
            case ITEM_CREATE -> buildItemCreateData(payload, localeList);
            case ITEM_GROUP_CREATE -> buildItemGroupCreateData(payload, localeList);
            case ITEM_MEMBER_ADD -> buildItemMemberAddData(payload, localeList);
            case CHAT_CREATE -> buildChatCreateData(payload, localeList);
            case CHAT_MESSAGE_CREATE -> buildChatMessageCreateData(payload, localeList);
            case CHAT_REACTION_INCOMING -> buildChatReactionIncomingData(payload, localeList);
            case CONTACT_REQUEST_INCOMING -> buildContactRequestIncomingData(payload, localeList);
            case CONTACT_ACCEPT_INCOMING -> buildContactAcceptIncomingData(payload, localeList);
            case COMMENT_CREATE -> buildCommentCreateData(payload, localeList);
            case COMMENT_REACTION_INCOMING -> buildCommentReactionIncoming(payload, localeList);
            case REMINDER -> buildReminderData(payload, localeList);
            default -> throw new MessagingException();
        };
    }

    private Map<Locale, FirebaseMessageData> buildItemCreateData(String payload, List<Locale> localeList) {
        Item item = jsonService.deserialize(payload, Item.class);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("item.create", null, locale);
            String body = item.getTitle();
            Map<String, String> dataMap = Map.of(ITEM_ID, item.getId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, body, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildItemGroupCreateData(String payload, List<Locale> localeList) {
        ItemGroup group = jsonService.deserialize(payload, ItemGroup.class);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("item.create_group", null, locale);
            String body = group.getTitle();
            Map<String, String> dataMap = Map.of(GROUP_ID, group.getId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, body, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildItemMemberAddData(String payload, List<Locale> localeList) {
        List<ItemGroupMember> memberList = jsonService.deserializeList(payload, ItemGroupMember.class);
        UUID groupId = memberList.get(0).getGroupId();
        List<UUID> userIdList = memberList.stream().map(ItemGroupMember::getUserId).toList();
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("item.add_members", null, locale);
            Map<String, String> dataMap = Map.of(GROUP_ID, groupId.toString());
            FirebaseMessageData data = new FirebaseMessageData(title, usernamesString, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildChatCreateData(String payload, List<Locale> localeList) {
        Chat chat = jsonService.deserialize(payload, Chat.class);
        List<UUID> userIdList = chat.getMembers();
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("chat.create", null, locale);
            Map<String, String> dataMap = Map.of(CHAT_ID, chat.getId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, usernamesString, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildChatMessageCreateData(String payload, List<Locale> localeList) {
        ChatMessage message = jsonService.deserialize(payload, ChatMessage.class);
        List<UUID> userIdList = List.of(message.getUserId());
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("chat.create_message", null, locale) + ": " + usernamesString;
            String body = message.getText();
            Map<String, String> dataMap = Map.of(CHAT_ID, message.getChatId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, body, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildChatReactionIncomingData(String payload, List<Locale> localeList) {
        ChatReaction reaction = jsonService.deserialize(payload, ChatReaction.class);
        List<UUID> userIdList = List.of(reaction.getUserId());
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("chat.reaction", null, locale);
            Map<String, String> dataMap = Map.of(CHAT_ID, reaction.getChatId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, usernamesString, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildContactRequestIncomingData(String payload, List<Locale> localeList) {
        ContactRequest request = jsonService.deserialize(payload, ContactRequest.class);
        List<UUID> userIdList = List.of(request.getRequesterId());
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("contact.request", null, locale);
            Map<String, String> dataMap = Map.of(USER_ID, request.getRequesterId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, usernamesString, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildContactAcceptIncomingData(String payload, List<Locale> localeList) {
        ContactRequest request = jsonService.deserialize(payload, ContactRequest.class);
        List<UUID> userIdList = List.of(request.getRecipientId());
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("contact.request_accept", null, locale);
            Map<String, String> dataMap = Map.of(USER_ID, request.getRecipientId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, usernamesString, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildCommentCreateData(String payload, List<Locale> localeList) {
        Comment comment = jsonService.deserialize(payload, Comment.class);
        List<UUID> userIdList = List.of(comment.getUserId());
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("comment.create", null, locale) + ": " + usernamesString;
            String body = comment.getText();
            Map<String, String> dataMap = Map.of(TARGET_ID, comment.getTargetId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, body, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildCommentReactionIncoming(String payload, List<Locale> localeList) {
        CommentReaction reaction = jsonService.deserialize(payload, CommentReaction.class);
        List<UUID> userIdList = List.of(reaction.getUserId());
        String usernamesString = getUsernames(userIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("comment.reaction", null, locale);
            Map<String, String> dataMap = Map.of(TARGET_ID, reaction.getTargetId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, usernamesString, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Locale, FirebaseMessageData> buildReminderData(String payload, List<Locale> localeList) {
        Reminder reminder = jsonService.deserialize(payload, Reminder.class);
        List<UUID> itemIdList = List.of(reminder.getTargetId());
        List<ItemInfo> itemInfoList = itemServiceClient.getAllItemInfoByIds(itemIdList);
        return localeList.stream().map(locale -> {
            String title = messageSource.getMessage("reminder", null, locale);
            String body = itemInfoList.get(0).getTitle();
            Map<String, String> dataMap = Map.of(ITEM_ID, reminder.getTargetId().toString());
            FirebaseMessageData data = new FirebaseMessageData(title, body, dataMap);
            return Pair.of(locale, data);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
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

    private String getUsernames(List<UUID> userIdList) {
        List<UserInfo> userList = userServiceClient.getAllUserInfoByIds(userIdList);
        List<String> usernameList = userList.stream().map(UserInfo::getUsername).toList();
        return String.join(", ", usernameList);
    }

    @Data
    @AllArgsConstructor
    private static class FirebaseMessageData {
        private String title;
        private String body;
        private Map<String, String> dataMap;
    }

}
