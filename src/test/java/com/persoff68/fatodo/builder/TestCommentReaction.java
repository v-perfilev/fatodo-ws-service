package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.CommentReaction;
import lombok.Builder;

import java.util.UUID;

public class TestCommentReaction extends CommentReaction {

    @Builder
    TestCommentReaction(UUID targetId, UUID userId) {
        super();
        super.setTargetId(targetId);
        super.setUserId(userId);
    }

    public static TestCommentReactionBuilder defaultBuilder() {
        return TestCommentReaction.builder()
                .targetId(UUID.randomUUID())
                .userId(UUID.randomUUID());
    }

    public CommentReaction toParent() {
        CommentReaction reaction = new CommentReaction();
        reaction.setTargetId(getTargetId());
        reaction.setUserId(getUserId());
        return reaction;
    }

}
