package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelDuplicatedException extends AbstractDatabaseException {

    public ModelDuplicatedException() {
        super(HttpStatus.NOT_FOUND, "Model duplicated in database");
    }

    public ModelDuplicatedException(Class<?> modelClass) {
        super(HttpStatus.NOT_FOUND, modelClass.getSimpleName() + " duplicated in database");
    }

}
