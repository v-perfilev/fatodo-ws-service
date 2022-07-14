package com.persoff68.fatodo.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.exception.KafkaException;
import com.persoff68.fatodo.model.Chat;
import com.persoff68.fatodo.model.ChatMessage;
import com.persoff68.fatodo.model.ChatReactions;
import com.persoff68.fatodo.model.ChatStatuses;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class ChatConsumer {

    private final static String WS_CHAT_TOPIC = "ws_chat";

    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = WS_CHAT_TOPIC, containerFactory = "chatContainerFactory")
    public void sendChatEvent(@Payload String value, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        switch (key) {
            case "new" -> handleChatNewEvent(value);
            case "update" -> handleChatUpdateEvent(value);
            case "last-message" -> handleLastMessageEvent(value);
            case "last-message-update" -> handleLastMessageUpdateEvent(value);
            case "message-new" -> handleMessageNewEvent(value);
            case "message-update" -> handleMessageUpdateEvent(value);
            case "statuses" -> handleStatusesEvent(value);
            case "reactions" -> handleReactionsEvent(value);
            default -> throw new KafkaException();
        }
        resetLatch();
    }

    private void handleChatNewEvent(String value) {
        WsEvent<Chat> event = extractWsEvent(value, Chat.class);
        chatService.handleChatNewEvent(event.getUserIds(), event.getContent());
    }

    private void handleChatUpdateEvent(String value) {
        WsEvent<Chat> event = extractWsEvent(value, Chat.class);
        chatService.handleChatUpdateEvent(event.getUserIds(), event.getContent());
    }

    private void handleLastMessageEvent(String value) {
        WsEvent<Chat> event = extractWsEvent(value, Chat.class);
        chatService.handleChatLastMessageEvent(event.getUserIds(), event.getContent());
    }

    private void handleLastMessageUpdateEvent(String value) {
        WsEvent<Chat> event = extractWsEvent(value, Chat.class);
        chatService.handleChatLastMessageUpdateEvent(event.getUserIds(), event.getContent());
    }

    private void handleMessageNewEvent(String value) {
        WsEvent<ChatMessage> event = extractWsEvent(value, ChatMessage.class);
        chatService.handleMessageNewEvent(event.getUserIds(), event.getContent());
    }

    private void handleMessageUpdateEvent(String value) {
        WsEvent<ChatMessage> event = extractWsEvent(value, ChatMessage.class);
        chatService.handleMessageUpdateEvent(event.getUserIds(), event.getContent());
    }

    private void handleStatusesEvent(String value) {
        WsEvent<ChatStatuses> event = extractWsEvent(value, ChatStatuses.class);
        chatService.handleStatusesEvent(event.getUserIds(), event.getContent());
    }

    private void handleReactionsEvent(String value) {
        WsEvent<ChatReactions> event = extractWsEvent(value, ChatReactions.class);
        chatService.handleReactionsEvent(event.getUserIds(), event.getContent());
    }

    private <T extends Serializable> WsEvent<T> extractWsEvent(String value, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(WsEvent.class, clazz);
            return objectMapper.readValue(value, javaType);
        } catch (JsonProcessingException e) {
            throw new KafkaException();
        }
    }

    private void resetLatch() {
        this.latch.countDown();
        this.latch = new CountDownLatch(1);
    }

}
