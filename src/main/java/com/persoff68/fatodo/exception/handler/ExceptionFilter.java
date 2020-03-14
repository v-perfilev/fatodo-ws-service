package com.persoff68.fatodo.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.attribute.ExceptionErrorAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {

    private final ExceptionErrorAttribute customErrorAttribute;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            HttpStatus status = customErrorAttribute.getStatus(e);
            Map<String, Object> errorAttributes = customErrorAttribute.getErrorAttributes(request, e);
            String responseBody = objectMapper.writeValueAsString(errorAttributes);
            response.sendError(status.value(), responseBody);
        }
    }
}
