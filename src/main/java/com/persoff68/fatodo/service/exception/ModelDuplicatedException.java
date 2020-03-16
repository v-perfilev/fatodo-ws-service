package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class ModelDuplicatedException extends AbstractDatabaseException {

    public ModelDuplicatedException() {
        super(HttpStatus.CONFLICT, "Model duplicated in database");
    }

    public ModelDuplicatedException(Class<?> modelClass) {
        super(HttpStatus.CONFLICT, modelClass.getSimpleName() + " duplicated in database");
    }

}
