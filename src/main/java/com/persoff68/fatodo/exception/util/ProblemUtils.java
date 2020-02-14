package com.persoff68.fatodo.exception.util;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.servlet.http.HttpServletRequest;

public class ProblemUtils {

    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private static final String ERROR_VIOLATION = "error.violation";
    private static final String ERROR_HTTP = "error.http.";

    public static Problem processConstraintViolationProblem(Problem problem, NativeWebRequest request) {
        return createBaseBuilder(problem, request)
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(MESSAGE_KEY, ERROR_VIOLATION)
                .build();
    }

    public static Problem processDefaultProblem(Problem problem, NativeWebRequest request) {
        ProblemBuilder builder = createBaseBuilder(problem, request)
                .withCause(((DefaultProblem) problem).getCause())
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance());
        problem.getParameters().forEach(builder::with);
        if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
            builder.with(MESSAGE_KEY, ERROR_HTTP + problem.getStatus().getStatusCode());
        }
        return builder.build();
    }

    private static ProblemBuilder createBaseBuilder(Problem problem, NativeWebRequest request) {
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
