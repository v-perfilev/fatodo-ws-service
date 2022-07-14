package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EventContactEvent implements Serializable {

    private UUID firstUserId;

    private UUID secondUserId;

}
