package com.persoff68.fatodo.model;


import com.persoff68.fatodo.model.constants.ClearEventType;
import lombok.Data;

import java.util.UUID;

@Data
public class ClearEvent {

    private ClearEventType type;

    private UUID id;

}
