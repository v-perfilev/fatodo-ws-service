package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelAlreadyExistsException extends AbstractDatabaseException {
    private static final String MESSAGE = "Model already exists in database";
    private static final String MESSAGE_WITH_PARAM = " already exists in database";
    private static final String FEEDBACK_CODE = "model.exists";

    public ModelAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE, FEEDBACK_CODE);
    }

    public ModelAlreadyExistsException(Class<?> modelClass) {
        super(HttpStatus.BAD_REQUEST, modelClass.getSimpleName() + MESSAGE_WITH_PARAM, FEEDBACK_CODE);
    }

}
