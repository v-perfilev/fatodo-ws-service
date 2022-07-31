package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.Request;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constants.WsContactDestination;
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
import static org.mockito.ArgumentMatchers.eq;
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
class ContactConsumerIT {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ContactConsumer contactConsumer;
    @SpyBean
    private WsService wsService;

    @MockBean
    UserServiceClient userServiceClient;

    private KafkaTemplate<String, WsEvent<Request>> contactKafkaTemplate;

    @BeforeEach
    void setup() {
        contactKafkaTemplate = buildKafkaTemplate();

        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    void testSendRequestIncomingEvent() throws InterruptedException {
        WsEvent<Request> event = TestWsEvent.<Request>defaultBuilder().content(new Request()).build().toParent();
        contactKafkaTemplate.send("ws_contact", "request-incoming", event);
        boolean messageConsumed = contactConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1))
                .sendMessage(any(), eq(WsContactDestination.CONTACT_REQUEST_INCOMING.getValue()), any());
    }

    @Test
    void testSendRequestOutcomingEvent() throws InterruptedException {
        WsEvent<Request> event = TestWsEvent.<Request>defaultBuilder().content(new Request()).build().toParent();
        contactKafkaTemplate.send("ws_contact", "request-outcoming", event);
        boolean messageConsumed = contactConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1))
                .sendMessage(any(), eq(WsContactDestination.CONTACT_REQUEST_OUTCOMING.getValue()), any());
    }

    @Test
    void testSendAcceptIncomingEvent() throws InterruptedException {
        WsEvent<Request> event = TestWsEvent.<Request>defaultBuilder().content(new Request()).build().toParent();
        contactKafkaTemplate.send("ws_contact", "accept-incoming", event);
        boolean messageConsumed = contactConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1))
                .sendMessage(any(), eq(WsContactDestination.CONTACT_ACCEPT_INCOMING.getValue()), any());
    }

    @Test
    void testSendAcceptOutcomingEvent() throws InterruptedException {
        WsEvent<Request> event = TestWsEvent.<Request>defaultBuilder().content(new Request()).build().toParent();
        contactKafkaTemplate.send("ws_contact", "accept-outcoming", event);
        boolean messageConsumed = contactConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1))
                .sendMessage(any(), eq(WsContactDestination.CONTACT_ACCEPT_OUTCOMING.getValue()), any());
    }

    private <T> KafkaTemplate<String, T> buildKafkaTemplate() {
        return KafkaUtils.buildJsonKafkaTemplate(embeddedKafkaBroker.getBrokersAsString());
    }

}
