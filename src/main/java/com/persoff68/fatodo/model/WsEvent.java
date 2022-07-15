package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class WsEvent<T> {

    private List<UUID> userIds;

    private T content;

}
