package com.persoff68.fatodo.web.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.builder.TestContactRequest;
import com.persoff68.fatodo.builder.TestUserInfo;
import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.ContactRequest;
import com.persoff68.fatodo.model.UserInfo;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constant.WsDestination;
import com.persoff68.fatodo.model.constant.WsEventType;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
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
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private WsService wsService;

    @MockBean
    UserServiceClient userServiceClient;

    private KafkaTemplate<String, WsEvent> wsKafkaTemplate;

    @BeforeEach
    void setup() {
        wsKafkaTemplate = buildKafkaTemplate();

        UserInfo userInfo = TestUserInfo.defaultBuilder().build().toParent();
        List<UserInfo> userInfoList = List.of(userInfo);
        when(userServiceClient.getAllUserInfoByIds(any())).thenReturn(userInfoList);
    }

    @Test
    void testSendEvent() throws Exception {
        ContactRequest contactRequest = TestContactRequest.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(contactRequest);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.CONTACT_REQUEST_INCOMING).payload(payload).build().toParent();

        wsKafkaTemplate.send("ws", event);
        boolean messageConsumed = eventConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService).sendMessages(any(), startsWith(WsDestination.ROOT.getValue()), any());
    }

    private <T> KafkaTemplate<String, T> buildKafkaTemplate() {
        return KafkaUtils.buildJsonKafkaTemplate(embeddedKafkaBroker.getBrokersAsString());
    }

}
