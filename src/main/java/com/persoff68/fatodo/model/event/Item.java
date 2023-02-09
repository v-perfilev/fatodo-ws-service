package com.persoff68.fatodo.model.event;

import com.persoff68.fatodo.model.AbstractAuditingModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends AbstractAuditingModel {

    private UUID groupId;

    private String title;

    private int priority;

    private String description;

    private int remindersCount;

    private boolean done;

    private boolean archived;

}
