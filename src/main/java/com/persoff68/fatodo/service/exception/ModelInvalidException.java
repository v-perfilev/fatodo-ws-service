package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelInvalidException extends AbstractDatabaseException {
    private static final String MESSAGE = "Model not valid";
    private static final String MESSAGE_WITH_PARAM = " not valid";
    private static final String FEEDBACK_CODE = "model.invalid";

    public ModelInvalidException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE, FEEDBACK_CODE);
    }

    public ModelInvalidException(Class<?> modelClass) {
        super(HttpStatus.BAD_REQUEST, modelClass.getSimpleName() + MESSAGE_WITH_PARAM, FEEDBACK_CODE);
    }

}
