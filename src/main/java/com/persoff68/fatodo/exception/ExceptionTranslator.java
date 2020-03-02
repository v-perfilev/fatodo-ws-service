package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
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
        Problem problem = entity.getBody();
        problem = processProblem(problem, request);
        return new ResponseEntity<>(problem, entity.getHeaders(), entity.getStatusCode());
    }

    private Problem processProblem(Problem problem, NativeWebRequest request) {
        if (problem instanceof ConstraintViolationProblem) {
            problem = processConstraintViolationProblem(problem, request);
        } else {
            problem = processDefaultProblem(problem, request);
        }
        return problem;
    }

    private Problem processConstraintViolationProblem(Problem problem, NativeWebRequest request) {
        return createBaseBuilder(problem, request)
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(MESSAGE_KEY, ERROR_VIOLATION)
                .build();
    }

    private Problem processDefaultProblem(Problem problem, NativeWebRequest request) {
        ProblemBuilder builder = createBaseBuilder(problem, request)
                .withCause(((AbstractThrowableProblem) problem).getCause())
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance());
        problem.getParameters().forEach(builder::with);
        if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
            builder.with(MESSAGE_KEY, ERROR_HTTP + problem.getStatus().getStatusCode());
        }
        return builder.build();
    }

    private ProblemBuilder createBaseBuilder(Problem problem, NativeWebRequest request) {
        ProblemBuilder builder = Problem.builder();
        HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
        if (problem != null && httpRequest != null) {
            builder
                    .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ExceptionTypes.DEFAULT_TYPE : problem.getType())
                    .withStatus(problem.getStatus())
                    .withTitle(problem.getTitle())
                    .with(PATH_KEY, httpRequest.getRequestURI());
        }
        return builder;
    }

}
