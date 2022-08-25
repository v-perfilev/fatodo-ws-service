package com.persoff68.fatodo.model.event;

import com.persoff68.fatodo.model.AbstractAuditingModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemGroup extends AbstractAuditingModel {

    private String title;

    private String color;

    private String imageFilename;

    private List<ItemGroupMember> members;

}
