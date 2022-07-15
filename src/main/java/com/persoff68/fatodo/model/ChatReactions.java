package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ChatReactions {

    private UUID chatId;

    private UUID messageId;

    private List<ChatReaction> reactions;

}
