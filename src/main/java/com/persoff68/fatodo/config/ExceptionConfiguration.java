package com.persoff68.fatodo.config;

import com.persoff68.fatodo.constant.ExceptionTypeConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class ExceptionConfiguration implements ProblemHandling, SecurityAdviceTrait {

    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private static final String ERROR_VIOLATION = "error.violation";
    private static final String ERROR_HTTP = "error.http.";

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }

        ResponseEntity<Problem> response = entity;
        Problem problem = entity.getBody();

        if (problem instanceof ConstraintViolationProblem) {
            response = processConstraintViolationProblem(entity, problem, request);
        } else if (problem instanceof DefaultProblem) {
            response = processDefaultProblem(entity, problem, request);
        }

        return response;
    }

    private ResponseEntity<Problem> processConstraintViolationProblem(ResponseEntity<Problem> entity, Problem problem, NativeWebRequest request) {
        ProblemBuilder builder = createBaseBuilder(problem, request)
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(MESSAGE_KEY, ERROR_VIOLATION);
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    private ResponseEntity<Problem> processDefaultProblem(ResponseEntity<Problem> entity, Problem problem, NativeWebRequest request) {
        ProblemBuilder builder = createBaseBuilder(problem, request)
                .withCause(((DefaultProblem) problem).getCause())
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance());
        problem.getParameters().forEach(builder::with);
        if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
            builder.with(MESSAGE_KEY, ERROR_HTTP + problem.getStatus().getStatusCode());
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    private ProblemBuilder createBaseBuilder(Problem problem, NativeWebRequest request) {
        ProblemBuilder builder = Problem.builder();
        HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
        if (problem != null && httpRequest != null) {
            builder
                    .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ExceptionTypeConstants.DEFAULT_TYPE : problem.getType())
                    .withStatus(problem.getStatus())
                    .withTitle(problem.getTitle())
                    .with(PATH_KEY, httpRequest.getRequestURI());
        }
        return builder;
    }

}
