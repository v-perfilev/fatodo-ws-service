package com.persoff68.fatodo.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.exception.KafkaException;
import com.persoff68.fatodo.model.Event;
import com.persoff68.fatodo.model.EventDelete;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.EventService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class EventConsumer {

    private static final String WS_EVENT_TOPIC = "ws_event";

    private final EventService eventService;
    private final ObjectMapper objectMapper;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = WS_EVENT_TOPIC, containerFactory = "eventContainerFactory")
    public void sendEvent(@Payload String value, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        switch (key) {
            case "new" -> handleEventNew(value);
            case "delete" -> handleEventDelete(value);
            default -> throw new KafkaException();
        }
        resetLatch();
    }

    private void handleEventNew(String value) {
        WsEvent<Event> event = extractWsEvent(value, Event.class);
        eventService.handleEvent(event.getUserIds(), event.getContent());
    }

    private void handleEventDelete(String value) {
        WsEvent<EventDelete> event = extractWsEvent(value, EventDelete.class);
        eventService.handleEventDelete(event.getUserIds(), event.getContent());
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
