package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.Event;
import com.persoff68.fatodo.model.EventDelete;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constants.WsEventDestination;
import com.persoff68.fatodo.service.WsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "kafka.bootstrapAddress=localhost:9092",
        "kafka.groupId=test",
        "kafka.partitions=1",
        "kafka.autoOffsetResetConfig=earliest"
})
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class EventConsumerIT {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private EventConsumer eventConsumer;
    @SpyBean
    private WsService wsService;

    @MockBean
    UserServiceClient userServiceClient;

    private KafkaTemplate<String, WsEvent<Event>> eventKafkaTemplate;
    private KafkaTemplate<String, WsEvent<EventDelete>> eventDeleteKafkaTemplate;

    @BeforeEach
    void setup() {
        eventKafkaTemplate = buildKafkaTemplate();
        eventDeleteKafkaTemplate = buildKafkaTemplate();

        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    void testSendEvent() throws InterruptedException {
        WsEvent<Event> event = TestWsEvent.<Event>defaultBuilder().content(new Event()).build().toParent();
        eventKafkaTemplate.send("ws_event", "new", event);
        boolean messageConsumed = eventConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsEventDestination.EVENT.getValue()), any());
    }

    @Test
    void testSendEventDelete() throws InterruptedException {
        WsEvent<EventDelete> event = TestWsEvent.<EventDelete>defaultBuilder().content(new EventDelete())
                .build().toParent();
        eventDeleteKafkaTemplate.send("ws_event", "delete", event);
        boolean messageConsumed = eventConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsEventDestination.EVENT.getValue()), any());
    }

    private <T> KafkaTemplate<String, T> buildKafkaTemplate() {
        return KafkaUtils.buildJsonKafkaTemplate(embeddedKafkaBroker.getBrokersAsString());
    }

}
