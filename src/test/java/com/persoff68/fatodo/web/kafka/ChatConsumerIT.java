package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.Chat;
import com.persoff68.fatodo.model.ChatMessage;
import com.persoff68.fatodo.model.ChatReactions;
import com.persoff68.fatodo.model.ChatStatuses;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constants.WsChatDestination;
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
public class ChatConsumerIT {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ChatConsumer chatConsumer;
    @SpyBean
    private WsService wsService;

    @MockBean
    UserServiceClient userServiceClient;

    private KafkaTemplate<String, WsEvent<Chat>> chatKafkaTemplate;
    private KafkaTemplate<String, WsEvent<ChatMessage>> chatMessageKafkaTemplate;
    private KafkaTemplate<String, WsEvent<ChatStatuses>> chatStatusesKafkaTemplate;
    private KafkaTemplate<String, WsEvent<ChatReactions>> chatReactionsKafkaTemplate;

    @BeforeEach
    void setup() {
        chatKafkaTemplate = buildKafkaTemplate();
        chatMessageKafkaTemplate = buildKafkaTemplate();
        chatStatusesKafkaTemplate = buildKafkaTemplate();
        chatReactionsKafkaTemplate = buildKafkaTemplate();

        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    void testSendChatNewEvent() throws InterruptedException {
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        chatKafkaTemplate.send("ws_chat", "new", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), eq(WsChatDestination.CHAT_NEW.getValue()), any());
    }

    @Test
    void testSendChatUpdateEvent() throws InterruptedException {
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        chatKafkaTemplate.send("ws_chat", "update", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), eq(WsChatDestination.CHAT_UPDATE.getValue()), any());
    }

    @Test
    void testSendChatLastMessageEvent() throws InterruptedException {
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        chatKafkaTemplate.send("ws_chat", "last-message", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsChatDestination.CHAT_LAST_MESSAGE.getValue()),
                any());
    }

    @Test
    void testSendChatLastMessageUpdateEvent() throws InterruptedException {
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        chatKafkaTemplate.send("ws_chat", "last-message-update", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(),
                startsWith(WsChatDestination.CHAT_LAST_MESSAGE_UPDATE.getValue()),
                any());
    }

    @Test
    void testSendMessageNewEvent() throws InterruptedException {
        WsEvent<ChatMessage> event =
                TestWsEvent.<ChatMessage>defaultBuilder().content(new ChatMessage()).build().toParent();
        chatMessageKafkaTemplate.send("ws_chat", "message-new", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsChatDestination.MESSAGE_NEW.getValue()),
                any());
    }

    @Test
    void testSendMessageUpdateEvent() throws InterruptedException {
        WsEvent<ChatMessage> event =
                TestWsEvent.<ChatMessage>defaultBuilder().content(new ChatMessage()).build().toParent();
        chatMessageKafkaTemplate.send("ws_chat", "message-update", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsChatDestination.MESSAGE_UPDATE.getValue()),
                any());
    }

    @Test
    void testSendStatusesEvent() throws InterruptedException {
        WsEvent<ChatStatuses> event = TestWsEvent.<ChatStatuses>defaultBuilder()
                .content(new ChatStatuses()).build().toParent();
        chatStatusesKafkaTemplate.send("ws_chat", "statuses", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsChatDestination.MESSAGE_STATUS.getValue()),
                any());
    }

    @Test
    void testSendReactionsEvent() throws InterruptedException {
        WsEvent<ChatReactions> event = TestWsEvent.<ChatReactions>defaultBuilder()
                .content(new ChatReactions()).build().toParent();
        chatReactionsKafkaTemplate.send("ws_chat", "reactions", event);
        boolean messageConsumed = chatConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsChatDestination.MESSAGE_REACTION.getValue()),
                any());
    }

    private <T> KafkaTemplate<String, T> buildKafkaTemplate() {
        return KafkaUtils.buildJsonKafkaTemplate(embeddedKafkaBroker.getBrokersAsString());
    }

}
