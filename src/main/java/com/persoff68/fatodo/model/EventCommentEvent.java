package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class EventCommentEvent {

    private UUID userId;

    private UUID parentId;

    private UUID targetId;

    private UUID commentId;

    private String reaction;

}