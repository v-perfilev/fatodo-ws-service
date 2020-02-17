package com.persoff68.fatodo.exception.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.WrappedRuntimeException;
import com.persoff68.fatodo.exception.util.ProblemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class ExceptionFilter implements Filter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            chain.doFilter(request, response);
        } catch (ThrowableProblem problem) {
            log.error("Problem in filter: " + problem.getMessage());
            handleResponseWithProblem(httpRequest, httpResponse, problem);
        } catch (RuntimeException e) {
            log.error("Runtime exception in filter: " + e.getMessage());
            Problem problem = new WrappedRuntimeException(e);
            handleResponseWithProblem(httpRequest, httpResponse, problem);
        }
    }

    private void handleResponseWithProblem(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Problem problem) throws IOException {
        NativeWebRequest nativeWebRequest = new ServletWebRequest(httpRequest);
        problem = ProblemUtils.processProblem(problem, nativeWebRequest);
        int statusCode = problem.getStatus() != null
                ? problem.getStatus().getStatusCode()
                : 500;
        String json = objectMapper.writeValueAsString(problem);
        httpResponse.setStatus(statusCode);
        httpResponse.getWriter().write(json);
    }

}
