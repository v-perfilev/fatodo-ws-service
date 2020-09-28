package com.persoff68.fatodo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class AbstractModel {

    @Id
    protected String id;

}

