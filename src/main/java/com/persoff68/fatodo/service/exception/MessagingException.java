package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class MessagingException extends AbstractException {
    private static final String MESSAGE = "Messaging error";

    public MessagingException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE);
    }

}
