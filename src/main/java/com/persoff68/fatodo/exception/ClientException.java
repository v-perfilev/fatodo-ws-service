package com.persoff68.fatodo.exception;

import org.springframework.http.HttpStatus;

public class ClientException extends AbstractException {
    public ClientException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal client error");
    }
}
