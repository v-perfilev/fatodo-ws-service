package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Comment;
import com.persoff68.fatodo.model.CommentReactions;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.service.CommentService;
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

    private final CommentService commentService;

    @PostMapping(value = "/new")
    public ResponseEntity<Void> sendCommentNewEvent(@RequestBody WsEvent<Comment> event) {
        commentService.handleCommentNewEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Void> sendCommentUpdateEvent(@RequestBody WsEvent<Comment> event) {
        commentService.handleCommentUpdateEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reactions")
    public ResponseEntity<Void> sendReactionsEvent(@RequestBody WsEvent<CommentReactions> event) {
        commentService.handleReactionsEvent(event.getUserIds(), event.getContent());
        return ResponseEntity.ok().build();
    }

}
