package com.persoff68.fatodo.model.event;

import com.persoff68.fatodo.model.AbstractAuditingModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends AbstractAuditingModel {

    private UUID groupId;

    private String title;

    private ItemType type;

    private ItemPriority priority;

    private DateParams date;

    private String description;

    private List<String> tags;

    private ItemStatus status;

    private boolean archived;

    @Data
    public static class DateParams {
        private int time;
        private int date;
        private int month;
        private int year;
        private String timezone;
    }

    public enum ItemPriority {
        LOW,
        NORMAL,
        HIGH
    }

    public enum ItemType {
        TASK,
        EVENT,
        REPETITION,
        NOTE
    }

    public enum ItemStatus {
        CREATED,
        WORK_IN_PROGRESS,
        COMPLETED,
        CLOSED
    }

}
