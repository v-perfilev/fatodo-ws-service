package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class PermissionException extends AbstractException {

    public PermissionException() {
        super(HttpStatus.BAD_REQUEST, "Permission error");
    }

    public PermissionException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
