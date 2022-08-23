package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.EventService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class EventConsumer {

    private static final String WS_TOPIC = "ws";

    private final EventService eventService;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = WS_TOPIC, containerFactory = "eventContainerFactory")
    public void sendEvent(WsEvent event) {
        eventService.handleEvent(event);
        resetLatch();
    }

    private void resetLatch() {
        this.latch.countDown();
        this.latch = new CountDownLatch(1);
    }

}
