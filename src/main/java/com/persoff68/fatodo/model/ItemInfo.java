package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemInfo extends AbstractModel {

    private String title;

}
