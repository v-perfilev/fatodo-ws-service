package com.persoff68.fatodo.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractDTO implements Serializable {

    protected String id;

}
