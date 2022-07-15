package com.persoff68.fatodo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public abstract class AbstractModel {

    @Id
    protected UUID id;

}

