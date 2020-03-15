package com.persoff68.fatodo.security.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "Full authentication required");
    }
}
