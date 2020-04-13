package com.persoff68.fatodo.exception;

import org.springframework.http.HttpStatus;

public class CacheException extends AbstractException {
    public CacheException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
