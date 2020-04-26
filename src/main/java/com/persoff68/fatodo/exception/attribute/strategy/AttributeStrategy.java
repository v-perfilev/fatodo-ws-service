package com.persoff68.fatodo.exception.attribute.strategy;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface AttributeStrategy {

    Map<String, Object> getErrorAttributes();

    HttpStatus getStatus();

    String getFeedbackCode();

    void addTimestamp();

    void addStatus();

    void addFeedbackCode();

    void addErrorDetails();

    void addPath();

}
