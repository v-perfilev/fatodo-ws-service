package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatMember {

    private UUID chatId;

    private UUID userId;

}

