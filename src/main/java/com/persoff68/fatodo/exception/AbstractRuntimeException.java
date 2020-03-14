package com.persoff68.fatodo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractRuntimeException extends RuntimeException {
    private HttpStatus status;

    protected AbstractRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
