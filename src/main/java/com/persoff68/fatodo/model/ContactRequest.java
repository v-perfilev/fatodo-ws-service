package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContactRequest extends AbstractAuditingModel {

    private UUID requesterId;

    private UUID recipientId;

}
