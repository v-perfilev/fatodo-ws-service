package com.persoff68.fatodo.web.rest.exception.handler;

import com.persoff68.fatodo.exception.handler.ExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandling {

    private final ExceptionTranslator exceptionTranslator;

}
