package com.persoff68.fatodo.service.exception;

import org.springframework.http.HttpStatus;

public final class CommonDatabaseException extends AbstractDatabaseException {

    public CommonDatabaseException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
