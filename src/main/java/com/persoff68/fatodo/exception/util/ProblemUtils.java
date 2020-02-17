package com.persoff68.fatodo.exception.util;

import com.persoff68.fatodo.exception.constant.ExceptionConstants;
import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.servlet.http.HttpServletRequest;

public class ProblemUtils {

    public static Problem processProblem(Problem problem, NativeWebRequest request) {
        if (problem instanceof ConstraintViolationProblem) {
            problem = ProblemUtils.processConstraintViolationProblem(problem, request);
        } else {
            problem = ProblemUtils.processDefaultProblem(problem, request);
        }
        return problem;
    }

    private static Problem processConstraintViolationProblem(Problem problem, NativeWebRequest request) {
        return createBaseBuilder(problem, request)
                .with(ExceptionConstants.VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(ExceptionConstants.MESSAGE_KEY, ExceptionConstants.ERROR_VIOLATION)
                .build();
    }

    private static Problem processDefaultProblem(Problem problem, NativeWebRequest request) {
        ProblemBuilder builder = createBaseBuilder(problem, request)
                .withCause(((AbstractThrowableProblem) problem).getCause())
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance());
        problem.getParameters().forEach(builder::with);
        if (!problem.getParameters().containsKey(ExceptionConstants.MESSAGE_KEY) && problem.getStatus() != null) {
            builder.with(ExceptionConstants.MESSAGE_KEY, ExceptionConstants.ERROR_HTTP + problem.getStatus().getStatusCode());
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
                    .with(ExceptionConstants.PATH_KEY, httpRequest.getRequestURI());
        }
        return builder;
    }

}
