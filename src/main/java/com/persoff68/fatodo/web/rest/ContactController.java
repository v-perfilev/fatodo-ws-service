package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Request;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ContactController.ENDPOINT)
@RequiredArgsConstructor
public class ContactController {
    static final String ENDPOINT = "/api/contact";

    private final ContactService contactService;

    @PostMapping(value = "/request-incoming")
    public ResponseEntity<Void> sendRequestIncomingEvent(@RequestBody WsEvent<Request> event) {
        contactService.handleRequestIncomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/request-outcoming")
    public ResponseEntity<Void> sendRequestOutcomingEvent(@RequestBody WsEvent<Request> event) {
        contactService.handleRequestOutcomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/accept-incoming")
    public ResponseEntity<Void> sendAcceptIncomingEvent(@RequestBody WsEvent<Request> event) {
        contactService.handleAcceptIncomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/accept-outcoming")
    public ResponseEntity<Void> sendAcceptOutcomingEvent(@RequestBody WsEvent<Request> event) {
        contactService.handleAcceptOutcomingEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

}
