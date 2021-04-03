package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.WsChatEvent;
import com.persoff68.fatodo.model.WsChatMessageEvent;
import com.persoff68.fatodo.model.WsChatReactionsEvent;
import com.persoff68.fatodo.model.WsChatStatusesEvent;
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
    public ResponseEntity<Void> sendChatNewEvent(@RequestBody WsChatEvent event) {
        chatEventService.handleChatNewEvent(event.getUserIds(), event.getChat());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Void> sendChatUpdateEvent(@RequestBody WsChatEvent event) {
        chatEventService.handleChatUpdateEvent(event.getUserIds(), event.getChat());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/last-message")
    public ResponseEntity<Void> sendChatLastMessageEvent(@RequestBody WsChatEvent event) {
        chatEventService.handleChatLastMessageEvent(event.getUserIds(), event.getChat());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/last-message-update")
    public ResponseEntity<Void> sendChatLastMessageUpdateEvent(@RequestBody WsChatEvent event) {
        chatEventService.handleChatLastMessageUpdateEvent(event.getUserIds(), event.getChat());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message-new")
    public ResponseEntity<Void> sendMessageNewEvent(@RequestBody WsChatMessageEvent event) {
        chatEventService.handleMessageNewEvent(event.getUserIds(), event.getMessage());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message-update")
    public ResponseEntity<Void> sendMessageUpdateEvent(@RequestBody WsChatMessageEvent event) {
        chatEventService.handleMessageUpdateEvent(event.getUserIds(), event.getMessage());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/statuses")
    public ResponseEntity<Void> sendStatusesEvent(@RequestBody WsChatStatusesEvent event) {
        chatEventService.handleStatusesEvent(event.getUserIds(), event.getStatuses());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reactions")
    public ResponseEntity<Void> sendReactionsEvent(@RequestBody WsChatReactionsEvent event) {
        chatEventService.handleReactionsEvent(event.getUserIds(),event.getReactions());
        return ResponseEntity.ok().build();
    }

}
