package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.model.Event;
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

    private final EventService eventService;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "ws_event", containerFactory = "eventContainerFactory")
    public void sendEvent(WsEvent<Event> event) {
        eventService.handleEvent(event.getUserIds(), event.getContent());
        resetLatch();
    }

    private void resetLatch() {
        this.latch.countDown();
        this.latch = new CountDownLatch(1);
    }

}
