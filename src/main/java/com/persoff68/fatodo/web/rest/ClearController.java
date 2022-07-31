package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.ClearEvent;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.ClearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ClearController.ENDPOINT)
@RequiredArgsConstructor
public class ClearController {
    static final String ENDPOINT = "/api/clear";

    private final ClearService clearService;

    @PostMapping
    public ResponseEntity<Void> sendClearEvent(@RequestBody WsEvent<ClearEvent> event) {
        clearService.handleClearEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

}
