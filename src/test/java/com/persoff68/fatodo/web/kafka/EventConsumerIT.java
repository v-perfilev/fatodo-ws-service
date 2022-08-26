package com.persoff68.fatodo.web.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.persoff68.fatodo.builder.TestContactRequest;
import com.persoff68.fatodo.builder.TestItemInfo;
import com.persoff68.fatodo.builder.TestUserInfo;
import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.ItemServiceClient;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.ItemInfo;
import com.persoff68.fatodo.model.UserInfo;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constant.WsDestination;
import com.persoff68.fatodo.model.constant.WsEventType;
import com.persoff68.fatodo.model.event.ContactRequest;
import com.persoff68.fatodo.service.WsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    private static final UUID ACTIVE_USER_ID = UUID.randomUUID();
    private static final String ACTIVE_USER_NAME = "active";
    private static final UUID INACTIVE_USER_ID = UUID.randomUUID();
    private static final String INACTIVE_USER_NAME = "inactive";

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
    @MockBean
    ItemServiceClient itemServiceClient;
    @MockBean
    SimpMessagingTemplate messagingTemplate;
    @MockBean
    SimpUserRegistry userRegistry;
    @MockBean
    FirebaseMessaging firebaseMessaging;

    private KafkaTemplate<String, WsEvent> wsKafkaTemplate;

    @BeforeEach
    void setup() {
        wsKafkaTemplate = buildKafkaTemplate();

        UserInfo activeUserInfo = TestUserInfo.defaultBuilder()
                .id(ACTIVE_USER_ID).username(ACTIVE_USER_NAME).build().toParent();
        UserInfo inactiveUserInfo = TestUserInfo.defaultBuilder()
                .id(INACTIVE_USER_ID).username(INACTIVE_USER_NAME).language("ru").build().toParent();
        when(userServiceClient.getAllUserInfoByIds(eq(List.of(ACTIVE_USER_ID))))
                .thenReturn(List.of(activeUserInfo));
        when(userServiceClient.getAllUserInfoByIds(eq(List.of(INACTIVE_USER_ID))))
                .thenReturn(List.of(inactiveUserInfo));
        when(userServiceClient.getAllUserInfoByIds(any()))
                .thenReturn(List.of(activeUserInfo, inactiveUserInfo));

        ItemInfo itemInfo = TestItemInfo.defaultBuilder().build().toParent();
        when(itemServiceClient.getAllItemInfoByIds(any())).thenReturn(List.of(itemInfo));

        SimpSubscription simpSubscription = Mockito.mock(SimpSubscription.class);
        SimpSession simpSession = Mockito.mock(SimpSession.class);
        SimpUser simpUser = Mockito.mock(SimpUser.class);
        when(simpUser.getName()).thenReturn(ACTIVE_USER_NAME);
        when(simpSession.getUser()).thenReturn(simpUser);
        when(simpSubscription.getSession()).thenReturn(simpSession);
        when(userRegistry.findSubscriptions(any())).thenReturn(Set.of(simpSubscription));
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
