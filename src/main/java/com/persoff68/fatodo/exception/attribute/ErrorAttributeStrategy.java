package com.persoff68.fatodo.exception.attribute;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface ErrorAttributeStrategy {

    Map<String, Object> getErrorAttributes();

    HttpStatus getStatus();

    void addTimestamp();

    void addStatus();

    void addErrorDetails();

    void addPath();

}
