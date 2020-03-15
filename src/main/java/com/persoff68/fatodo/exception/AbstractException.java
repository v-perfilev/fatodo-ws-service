package com.persoff68.fatodo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractException extends RuntimeException {
    private HttpStatus status;

    protected AbstractException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
