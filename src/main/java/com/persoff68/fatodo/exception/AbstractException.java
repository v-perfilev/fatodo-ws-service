package com.persoff68.fatodo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractException extends RuntimeException {
    private final HttpStatus status;
    private final String translationCode;

    protected AbstractException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.translationCode = null;
    }

    protected AbstractException(HttpStatus status, String message, String translationCode) {
        super(message);
        this.status = status;
        this.translationCode = translationCode;
    }
}
