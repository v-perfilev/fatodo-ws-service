package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.ExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class ServiceExceptionHandling {

    private final ExceptionTranslator exceptionTranslator;

}
