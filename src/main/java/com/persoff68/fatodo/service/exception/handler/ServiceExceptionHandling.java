package com.persoff68.fatodo.service.exception.handler;

import com.persoff68.fatodo.exception.handler.ExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class ServiceExceptionHandling {

    private final ExceptionTranslator exceptionTranslator;

}
