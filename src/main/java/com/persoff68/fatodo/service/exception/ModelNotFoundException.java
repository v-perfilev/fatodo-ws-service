package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelNotFoundException extends AbstractDatabaseException {
    private static final String MESSAGE = "Model not found in database";
    private static final String MESSAGE_WITH_PARAM = " not found in database";
    private static final String FEEDBACK_CODE = "model.notFound";

    public ModelNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE, FEEDBACK_CODE);
    }

    public ModelNotFoundException(Class<?> modelClass) {
        super(HttpStatus.NOT_FOUND, modelClass.getSimpleName() + MESSAGE_WITH_PARAM, FEEDBACK_CODE);
    }

}
