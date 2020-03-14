package com.persoff68.fatodo.web.rest.exception;

import com.persoff68.fatodo.exception.AbstractRuntimeException;
import org.springframework.http.HttpStatus;

public class FormNotValidException extends AbstractRuntimeException {
    public FormNotValidException() {
        super(HttpStatus.BAD_REQUEST, "Form data not valid");
    }
}
