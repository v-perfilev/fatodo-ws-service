package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Event;
import com.persoff68.fatodo.model.EventDelete;
import com.persoff68.fatodo.model.WsEvent;
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

    @PostMapping("/new")
    public ResponseEntity<Void> sendEvent(@RequestBody WsEvent<Event> event) {
        eventService.handleEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> sendEventDelete(@RequestBody WsEvent<EventDelete> event) {
        eventService.handleEventDelete(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

}
