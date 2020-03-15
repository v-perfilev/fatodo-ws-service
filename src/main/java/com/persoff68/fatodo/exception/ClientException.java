package com.persoff68.fatodo.exception;

import org.springframework.http.HttpStatus;

public class ClientException extends AbstractException {
    public ClientException(HttpStatus status, String message) {
        super(status, message);
    }
}
