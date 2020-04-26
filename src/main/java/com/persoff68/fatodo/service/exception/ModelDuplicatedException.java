package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelDuplicatedException extends AbstractDatabaseException {
    private static final String MESSAGE = "Model duplicated in database";
    private static final String MESSAGE_WITH_PARAM = " duplicated in database";
    private static final String FEEDBACK_CODE = "model.duplicated";

    public ModelDuplicatedException() {
        super(HttpStatus.CONFLICT, MESSAGE, FEEDBACK_CODE);
    }

    public ModelDuplicatedException(Class<?> modelClass) {
        super(HttpStatus.CONFLICT, modelClass.getSimpleName() + MESSAGE_WITH_PARAM, FEEDBACK_CODE);
    }

}
