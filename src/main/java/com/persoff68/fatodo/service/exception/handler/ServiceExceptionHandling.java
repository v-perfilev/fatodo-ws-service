package com.persoff68.fatodo.service.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ServiceExceptionHandling {

}
