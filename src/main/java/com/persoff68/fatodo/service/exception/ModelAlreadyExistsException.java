package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelAlreadyExistsException extends AbstractDatabaseException {

    public ModelAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "Model already exits in database");
    }

    public ModelAlreadyExistsException(Class<?> modelClass) {
        super(HttpStatus.BAD_REQUEST, modelClass.getSimpleName() + " already exits in database");
    }

}
