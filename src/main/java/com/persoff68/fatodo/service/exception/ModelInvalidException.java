package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelInvalidException extends AbstractDatabaseException {

    public ModelInvalidException() {
        super(HttpStatus.BAD_REQUEST, "Model not valid");
    }

    public ModelInvalidException(Class<?> modelClass) {
        super(HttpStatus.BAD_REQUEST, modelClass.getSimpleName() + " not valid");
    }

}
