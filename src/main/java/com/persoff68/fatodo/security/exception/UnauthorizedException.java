package com.persoff68.fatodo.security.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractException {
    private static final String MESSAGE = "Authentication required";
    private static final String FEEDBACK_CODE = "security.unauthorized";

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, MESSAGE, FEEDBACK_CODE);
    }
}
