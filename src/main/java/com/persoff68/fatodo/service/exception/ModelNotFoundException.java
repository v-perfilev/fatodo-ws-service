package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelNotFoundException extends AbstractDatabaseException {

    public ModelNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Model not found in database");
    }

    public ModelNotFoundException(Class<?> modelClass) {
        super(HttpStatus.NOT_FOUND, modelClass.getSimpleName() + " not found in database");
    }

}
