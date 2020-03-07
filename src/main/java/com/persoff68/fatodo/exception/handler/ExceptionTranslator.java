package com.persoff68.fatodo.exception.handler;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {
    private final String MESSAGE_KEY = "message";
    private final String PATH_KEY = "path";
    private final String VIOLATIONS_KEY = "violations";
    private final String ERROR_VIOLATION = "error.violation";
    private final String ERROR_HTTP = "error.http.";

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
        String route = httpRequest.getRequestURI();
        Problem problem = entity.getBody();
        problem = handleProblem(problem, route);
        return new ResponseEntity<>(problem, entity.getHeaders(), entity.getStatusCode());
    }

    public ResponseEntity<Problem> process(Problem problem, String route) {
        problem = handleProblem(problem, route);
        return ResponseEntity.status(problem.getStatus().getStatusCode())
                .contentType(MediaType.APPLICATION_JSON).body(problem);
    }

    private Problem handleProblem(Problem problem, String route) {
        if (problem instanceof ConstraintViolationProblem) {
            return handleConstraintViolationProblem(problem, route);
        } else {
            return handleDefaultProblem(problem, route);
        }
    }

    private Problem handleConstraintViolationProblem(Problem problem, String route) {
        return createBaseBuilder(problem, route)
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(MESSAGE_KEY, ERROR_VIOLATION)
                .build();
    }

    private Problem handleDefaultProblem(Problem problem, String route) {
        ProblemBuilder builder = createBaseBuilder(problem, route)
                .withCause(((AbstractThrowableProblem) problem).getCause())
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance());
        problem.getParameters().forEach(builder::with);
        if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
            builder.with(MESSAGE_KEY, ERROR_HTTP + problem.getStatus().getStatusCode());
        }
        return builder.build();
    }

    private ProblemBuilder createBaseBuilder(Problem problem, String route) {
        ProblemBuilder builder = Problem.builder();
        if (problem != null && route != null) {
            builder
                    .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ExceptionTypes.DEFAULT_TYPE : problem.getType())
                    .withStatus(problem.getStatus())
                    .withTitle(problem.getTitle())
                    .with(PATH_KEY, route);
        }
        return builder;
    }

}
