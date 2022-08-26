package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.Comment;
import lombok.Builder;

import java.util.UUID;

public class TestComment extends Comment {

    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestComment(UUID id, UUID targetId, String text, UUID userId) {
        super();
        super.setId(id);
        super.setTargetId(targetId);
        super.setText(text);
        super.setUserId(userId);
    }

    public static TestCommentBuilder defaultBuilder() {
        return TestComment.builder()
                .id(UUID.randomUUID())
                .targetId(UUID.randomUUID())
                .text(DEFAULT_VALUE)
                .userId(UUID.randomUUID());
    }

    public Comment toParent() {
        Comment comment = new Comment();
        comment.setId(getId());
        comment.setTargetId(getTargetId());
        comment.setText(getText());
        comment.setUserId(getUserId());
        return comment;
    }

}
