package com.persoff68.fatodo.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.InheritedProblem;
import com.persoff68.fatodo.exception.RuntimeProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Integer.MIN_VALUE)
@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {

    private final ExceptionTranslator exceptionTranslator;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            ResponseEntity<Problem> responseEntity = createResponseEntity(e, request);
            writeValuesToResponse(response, responseEntity);
        }
    }

    private void writeValuesToResponse(HttpServletResponse response, ResponseEntity<Problem> responseEntity) throws IOException {
        responseEntity.getHeaders().forEach((key, value) -> response.setHeader(key, String.join(", ", value)));
        response.setStatus(responseEntity.getStatusCodeValue());
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
    }

    private ResponseEntity<Problem> createResponseEntity(Exception e, HttpServletRequest request) {
        ThrowableProblem problem = handleException(e);
        String uri = request.getRequestURI();
        return exceptionTranslator.process(problem, uri);
    }

    private ThrowableProblem handleException(Exception e) {
        if (e instanceof ThrowableProblem) {
            return (ThrowableProblem) e;
        } else if (e instanceof RuntimeException) {
            return new RuntimeProblem(e);
        } else {
            return new InheritedProblem(e);
        }
    }
}
