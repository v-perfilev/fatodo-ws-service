package com.persoff68.fatodo.model.event;

import com.persoff68.fatodo.model.AbstractAuditingModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContactRelation extends AbstractAuditingModel {

    private UUID firstUserId;

    private UUID secondUserId;

}
