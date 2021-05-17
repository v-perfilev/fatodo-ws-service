package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Chat;
import com.persoff68.fatodo.model.ChatMessage;
import com.persoff68.fatodo.model.ChatReactions;
import com.persoff68.fatodo.model.ChatStatuses;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.ChatEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ChatController.ENDPOINT)
@RequiredArgsConstructor
public class ChatController {
    static final String ENDPOINT = "/api/chat";

    private final ChatEventService chatEventService;

    @PostMapping(value = "/new")
    public ResponseEntity<Void> sendChatNewEvent(@RequestBody WsEvent<Chat> event) {
        chatEventService.handleChatNewEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Void> sendChatUpdateEvent(@RequestBody WsEvent<Chat> event) {
        chatEventService.handleChatUpdateEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/last-message")
    public ResponseEntity<Void> sendChatLastMessageEvent(@RequestBody WsEvent<Chat> event) {
        chatEventService.handleChatLastMessageEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/last-message-update")
    public ResponseEntity<Void> sendChatLastMessageUpdateEvent(@RequestBody WsEvent<Chat> event) {
        chatEventService.handleChatLastMessageUpdateEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message-new")
    public ResponseEntity<Void> sendMessageNewEvent(@RequestBody WsEvent<ChatMessage> event) {
        chatEventService.handleMessageNewEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message-update")
    public ResponseEntity<Void> sendMessageUpdateEvent(@RequestBody WsEvent<ChatMessage> event) {
        chatEventService.handleMessageUpdateEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/statuses")
    public ResponseEntity<Void> sendStatusesEvent(@RequestBody WsEvent<ChatStatuses> event) {
        chatEventService.handleStatusesEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reactions")
    public ResponseEntity<Void> sendReactionsEvent(@RequestBody WsEvent<ChatReactions> event) {
        chatEventService.handleReactionsEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

}
