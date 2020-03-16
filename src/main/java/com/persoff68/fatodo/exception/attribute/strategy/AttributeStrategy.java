package com.persoff68.fatodo.exception.attribute.strategy;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface AttributeStrategy {

    Map<String, Object> getErrorAttributes();

    HttpStatus getStatus();

    void addTimestamp();

    void addStatus();

    void addErrorDetails();

    void addPath();

}
