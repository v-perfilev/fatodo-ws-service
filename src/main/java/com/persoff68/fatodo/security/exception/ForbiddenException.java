package com.persoff68.fatodo.security.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "Access is denied");
    }
}
