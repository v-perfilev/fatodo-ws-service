package com.persoff68.fatodo.model;


import com.persoff68.fatodo.config.constant.AppConstants;
import com.persoff68.fatodo.model.constants.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Event extends AbstractModel implements Serializable {
    @Serial
    private static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private EventType type;

    private EventContactEvent contactEvent;

    private EventItemEvent itemEvent;

    private EventCommentEvent commentEvent;

    private EventChatEvent chatEvent;

    private EventReminderEvent reminderEvent;

    private Date createdAt;

}
