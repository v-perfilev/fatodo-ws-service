package com.persoff68.fatodo.exception;

import org.springframework.http.HttpStatus;

public class KafkaException extends AbstractException {
    public KafkaException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Kafka error");
    }
}
