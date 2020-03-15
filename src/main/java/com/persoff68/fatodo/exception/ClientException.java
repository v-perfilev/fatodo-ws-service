package com.persoff68.fatodo.exception;

import org.springframework.http.HttpStatus;

public class ClientException extends AbstractException {
    protected ClientException(HttpStatus status, String message) {
        super(status, message);
    }
}
