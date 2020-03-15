package com.persoff68.fatodo.web.rest.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class FormNotValidException extends AbstractException {
    public FormNotValidException() {
        super(HttpStatus.BAD_REQUEST, "Form data not valid");
    }
}
