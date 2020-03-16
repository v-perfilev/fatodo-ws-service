package com.persoff68.fatodo.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.AbstractException;
import com.persoff68.fatodo.exception.attribute.AttributeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ExceptionHandling {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<String> handleAbstractException(HttpServletRequest request, AbstractException e) {
        return AttributeHandler.from(request, e).getResponseEntity(objectMapper);
    }

}
