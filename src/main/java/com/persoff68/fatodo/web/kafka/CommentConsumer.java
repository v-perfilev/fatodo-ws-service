package com.persoff68.fatodo.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.exception.KafkaException;
import com.persoff68.fatodo.model.Comment;
import com.persoff68.fatodo.model.CommentReactions;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class CommentConsumer {

    private static final String WS_COMMENT_TOPIC = "ws_comment";

    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = WS_COMMENT_TOPIC, containerFactory = "commentContainerFactory")
    public void sendCommentEvent(@Payload String value, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        switch (key) {
            case "new" -> handleCommentNewEvent(value);
            case "update" -> handleCommentUpdateEvent(value);
            case "reactions" -> handleReactionsEvent(value);
            default -> throw new KafkaException();
        }
        resetLatch();
    }

    private void handleCommentNewEvent(String value) {
        WsEvent<Comment> event = extractWsEvent(value, Comment.class);
        commentService.handleCommentNewEvent(event.getUserIds(), event.getContent());
    }

    private void handleCommentUpdateEvent(String value) {
        WsEvent<Comment> event = extractWsEvent(value, Comment.class);
        commentService.handleCommentUpdateEvent(event.getUserIds(), event.getContent());
    }

    private void handleReactionsEvent(String value) {
        WsEvent<CommentReactions> event = extractWsEvent(value, CommentReactions.class);
        commentService.handleReactionsEvent(event.getUserIds(), event.getContent());
    }

    private <T extends Serializable> WsEvent<T> extractWsEvent(String value, Class<T> clazz) {
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
