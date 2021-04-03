package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractAuditingModel extends AbstractModel {

    protected UUID createdBy;
    protected Date createdAt;
    protected UUID lastModifiedBy;
    protected Date lastModifiedAt;

}
