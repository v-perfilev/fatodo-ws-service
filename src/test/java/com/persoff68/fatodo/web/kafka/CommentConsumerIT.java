package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.Comment;
import com.persoff68.fatodo.model.CommentReactions;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constants.WsCommentDestination;
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
public class CommentConsumerIT {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private CommentConsumer commentConsumer;
    @SpyBean
    private WsService wsService;

    @MockBean
    UserServiceClient userServiceClient;

    private KafkaTemplate<String, WsEvent<Comment>> commentKafkaTemplate;
    private KafkaTemplate<String, WsEvent<CommentReactions>> commentReactionsKafkaTemplate;

    @BeforeEach
    void setup() {
        commentKafkaTemplate = buildKafkaTemplate();
        commentReactionsKafkaTemplate = buildKafkaTemplate();

        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    void testSendCommentNewEvent() throws InterruptedException {
        WsEvent<Comment> event = TestWsEvent.<Comment>defaultBuilder().content(new Comment()).build().toParent();
        commentKafkaTemplate.send("ws_comment", "new", event);
        boolean messageConsumed = commentConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsCommentDestination.COMMENT_NEW.getValue()), any());
    }

    @Test
    void testSendCommentUpdateEvent() throws InterruptedException {
        WsEvent<Comment> event = TestWsEvent.<Comment>defaultBuilder().content(new Comment()).build().toParent();
        commentKafkaTemplate.send("ws_comment", "update", event);
        boolean messageConsumed = commentConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsCommentDestination.COMMENT_UPDATE.getValue()),
                any());
    }

    @Test
    void testSendReactionsEvent() throws InterruptedException {
        WsEvent<CommentReactions> event = TestWsEvent.<CommentReactions>defaultBuilder()
                .content(new CommentReactions()).build().toParent();
        commentReactionsKafkaTemplate.send("ws_comment", "reactions", event);
        boolean messageConsumed = commentConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(wsService, times(1)).sendMessage(any(), startsWith(WsCommentDestination.COMMENT_REACTION.getValue()),
                any());
    }

    private <T> KafkaTemplate<String, T> buildKafkaTemplate() {
        return KafkaUtils.buildJsonKafkaTemplate(embeddedKafkaBroker.getBrokersAsString());
    }

}
