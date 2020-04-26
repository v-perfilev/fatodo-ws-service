package com.persoff68.fatodo.web.rest.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class InvalidFormException extends AbstractException {
    private static final String MESSAGE = "Input data is incorrect";
    private static final String FEEDBACK_CODE = "form.invalid";

    public InvalidFormException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE, FEEDBACK_CODE);
    }
}
