package com.persoff68.fatodo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.attribute.AttributeHandler;
import com.persoff68.fatodo.security.exception.ForbiddenException;
import com.persoff68.fatodo.security.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityProblemSupport implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Exception exception = new UnauthorizedException();
        AttributeHandler.from(request, exception).sendError(objectMapper, response);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Exception exception = new ForbiddenException();
        AttributeHandler.from(request, exception).sendError(objectMapper, response);
    }
}
