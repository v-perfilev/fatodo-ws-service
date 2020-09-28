package com.persoff68.fatodo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public abstract class AbstractModel implements Serializable {

    @Id
    protected String id;

}

