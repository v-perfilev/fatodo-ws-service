package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.WsEventWithUsers;
import com.persoff68.fatodo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EventController.ENDPOINT)
@RequiredArgsConstructor
public class EventController {
    static final String ENDPOINT = "/api/event";

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Void> sendEvent(@RequestBody WsEventWithUsers event) {
        eventService.handleEvent(event);
        return ResponseEntity.ok().build();
    }

}
