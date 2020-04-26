package com.persoff68.fatodo.exception.attribute.strategy;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface AttributeStrategy {

    Map<String, Object> getErrorAttributes();

    HttpStatus getStatus();

    String getTranslationCode();

    void addTimestamp();

    void addStatus();

    void addTranslationCode();

    void addErrorDetails();

    void addPath();

}
