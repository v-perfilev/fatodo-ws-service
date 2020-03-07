package com.persoff68.fatodo.web.rest.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class RestExceptionHandling {

}
