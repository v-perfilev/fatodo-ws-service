package com.persoff68.fatodo.security.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractException {
    private static final String MESSAGE = "Access forbidden";
    private static final String FEEDBACK_CODE = "security.forbidden";

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, MESSAGE, FEEDBACK_CODE);
    }
}
