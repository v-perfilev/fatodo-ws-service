package com.persoff68.fatodo.model;


import com.persoff68.fatodo.model.constants.EventDeleteType;
import lombok.Data;

import java.util.UUID;

@Data
public class EventDelete {

    private EventDeleteType type;

    private UUID id;

}
