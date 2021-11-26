package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Comment;
import com.persoff68.fatodo.model.CommentReactions;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.CommentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CommentController.ENDPOINT)
@RequiredArgsConstructor
public class CommentController {
    static final String ENDPOINT = "/api/comment";

    private final CommentEventService commentEventService;

    @PostMapping(value = "/new")
    public ResponseEntity<Void> sendCommentNewEvent(@RequestBody WsEvent<Comment> event) {
        commentEventService.handleCommentNewEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Void> sendCommentUpdateEvent(@RequestBody WsEvent<Comment> event) {
        commentEventService.handleCommentUpdateEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reactions")
    public ResponseEntity<Void> sendReactionsEvent(@RequestBody WsEvent<CommentReactions> event) {
        commentEventService.handleReactionsEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

}
