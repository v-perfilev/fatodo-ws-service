package com.persoff68.fatodo.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.exception.KafkaException;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.ContactService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class ContactConsumer {

    private static final String WS_CONTACT_TOPIC = "ws_contact";

    private final ContactService contactService;

    private final ObjectMapper objectMapper;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = WS_CONTACT_TOPIC, containerFactory = "contactContainerFactory")
    public void sendChatEvent(@Payload String value, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        switch (key) {
            case "request-incoming" -> handleRequestIncoming(value);
            case "request-outcoming" -> handleRequestOutcoming(value);
            case "accept-incoming" -> handleAcceptIncoming(value);
            case "accept-outcoming" -> handleAcceptOutcoming(value);
            case "delete-request-incoming" -> handleDeleteRequestIncoming(value);
            case "delete-request-outcoming" -> handleDeleteRequestOutcoming(value);
            case "delete-relation" -> handleDeleteRelation(value);
            default -> throw new KafkaException();
        }
        resetLatch();
    }

    private void handleRequestIncoming(String value) {
        WsEvent<UUID> event = extractWsEvent(value, UUID.class);
        contactService.handleRequestIncomingEvent(event.getUserIds(), event.getContent());
    }

    private void handleRequestOutcoming(String value) {
        WsEvent<UUID> event = extractWsEvent(value, UUID.class);
        contactService.handleRequestOutcomingEvent(event.getUserIds(), event.getContent());
    }

    private void handleAcceptIncoming(String value) {
        WsEvent<UUID> event = extractWsEvent(value, UUID.class);
        contactService.handleAcceptIncomingEvent(event.getUserIds(), event.getContent());
    }

    private void handleAcceptOutcoming(String value) {
        WsEvent<UUID> event = extractWsEvent(value, UUID.class);
        contactService.handleAcceptOutcomingEvent(event.getUserIds(), event.getContent());
    }

    private void handleDeleteRequestIncoming(String value) {
        WsEvent<UUID> event = extractWsEvent(value, UUID.class);
        contactService.handleDeleteRequestIncomingEvent(event.getUserIds(), event.getContent());
    }

    private void handleDeleteRequestOutcoming(String value) {
        WsEvent<UUID> event = extractWsEvent(value, UUID.class);
        contactService.handleDeleteRequestOutcomingEvent(event.getUserIds(), event.getContent());
    }

    private void handleDeleteRelation(String value) {
        WsEvent<UUID> event = extractWsEvent(value, UUID.class);
        contactService.handleDeleteRelationEvent(event.getUserIds(), event.getContent());
    }

    private <T> WsEvent<T> extractWsEvent(String value, Class<T> clazz) {
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
