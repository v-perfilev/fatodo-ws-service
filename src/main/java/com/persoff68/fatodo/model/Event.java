package com.persoff68.fatodo.model;


import com.persoff68.fatodo.model.constants.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Event extends AbstractModel {

    private EventType type;

    private EventContactEvent contactEvent;

    private EventItemEvent itemEvent;

    private EventCommentEvent commentEvent;

    private EventChatEvent chatEvent;

    private EventReminderEvent reminderEvent;

    private Date createdAt;

}
