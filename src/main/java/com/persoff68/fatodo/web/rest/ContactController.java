package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(ContactController.ENDPOINT)
@RequiredArgsConstructor
public class ContactController {
    static final String ENDPOINT = "/api/contact";

    private final ContactService contactService;

    @PostMapping(value = "/request-incoming")
    public ResponseEntity<Void> sendRequestIncomingEvent(@RequestBody WsEvent<UUID> event) {
        contactService.handleRequestIncomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/request-outcoming")
    public ResponseEntity<Void> sendRequestOutcomingEvent(@RequestBody WsEvent<UUID> event) {
        contactService.handleRequestOutcomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/accept-incoming")
    public ResponseEntity<Void> sendAcceptIncomingEvent(@RequestBody WsEvent<UUID> event) {
        contactService.handleAcceptIncomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/accept-outcoming")
    public ResponseEntity<Void> sendAcceptOutcomingEvent(@RequestBody WsEvent<UUID> event) {
        contactService.handleAcceptOutcomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/delete-request-incoming")
    public ResponseEntity<Void> sendDeleteIncomingRequestEvent(@RequestBody WsEvent<UUID> event) {
        contactService.handleDeleteRequestIncomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/delete-request-outcoming")
    public ResponseEntity<Void> sendDeleteOutcomingRequestEvent(@RequestBody WsEvent<UUID> event) {
        contactService.handleDeleteRequestOutcomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/delete-relation")
    public ResponseEntity<Void> sendDeleteRelationEvent(@RequestBody WsEvent<UUID> event) {
        contactService.handleDeleteRelationEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

}
