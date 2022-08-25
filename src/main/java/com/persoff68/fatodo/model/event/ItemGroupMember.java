package com.persoff68.fatodo.model.event;

import lombok.Data;

import java.util.UUID;

@Data
public class ItemGroupMember {

    private UUID groupId;

    private UUID userId;

    private Permission permission;

    public enum Permission {
        ADMIN,
        EDIT,
        READ
    }

}

