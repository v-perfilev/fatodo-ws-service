package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelNotFoundException extends AbstractDatabaseException {

    public ModelNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Model not found in database");
    }

}
