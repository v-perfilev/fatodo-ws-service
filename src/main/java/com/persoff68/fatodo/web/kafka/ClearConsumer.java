package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.model.ClearEvent;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.ClearService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class ClearConsumer {

    private static final String WS_CLEAR_TOPIC = "ws_clear";

    private final ClearService clearService;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = WS_CLEAR_TOPIC, containerFactory = "clearContainerFactory")
    public void sendEvent(WsEvent<ClearEvent> event) {
        clearService.handleClearEvent(event.getUserIds(), event.getContent());
        resetLatch();
    }

    private void resetLatch() {
        this.latch.countDown();
        this.latch = new CountDownLatch(1);
    }

}
