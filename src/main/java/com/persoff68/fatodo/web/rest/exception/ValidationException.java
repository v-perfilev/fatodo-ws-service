package com.persoff68.fatodo.web.rest.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ValidationException extends AbstractException {
    public ValidationException() {
        super(HttpStatus.BAD_REQUEST, "Input data not valid");
    }
}
