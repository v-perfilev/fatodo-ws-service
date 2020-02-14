package com.persoff68.fatodo.exception.filter;

import com.persoff68.fatodo.exception.WrappedRuntimeException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (ThrowableProblem problem) {
            handleResponseWithProblem(response, problem);
        } catch (RuntimeException e) {
            Problem problem = new WrappedRuntimeException(e);
            handleResponseWithProblem(response, problem);
        }
    }

    private void handleResponseWithProblem(ServletResponse response, Problem problem) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        int statusCode = problem.getStatus() != null ? problem.getStatus().getStatusCode() : 500;
        String message = problem.toString();
        httpServletResponse.setStatus(statusCode);
        httpServletResponse.getWriter().write(message);
    }

}
