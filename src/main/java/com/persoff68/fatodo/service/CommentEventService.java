package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Comment;
import com.persoff68.fatodo.model.CommentReactions;
import com.persoff68.fatodo.model.constants.WsCommentDestination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentEventService {

    private final WsService wsService;

    public void handleCommentNewEvent(List<UUID> userIdList, Comment comment) {
        UUID threadId = comment.getThreadId();
        String destination = WsCommentDestination.COMMENT_NEW.getValue() + threadId;
        wsService.sendMessage(userIdList, destination, comment);
    }

    public void handleCommentUpdateEvent(List<UUID> userIdList, Comment comment) {
        UUID threadId = comment.getThreadId();
        String destination = WsCommentDestination.COMMENT_UPDATE.getValue() + threadId;
        wsService.sendMessage(userIdList, destination, comment);
    }

    public void handleReactionsEvent(List<UUID> userIdList, CommentReactions reactions) {
        UUID threadId = reactions.getThreadId();
        String destination = WsCommentDestination.COMMENT_REACTION.getValue() + threadId;
        wsService.sendMessage(userIdList, destination, reactions);
    }

}
