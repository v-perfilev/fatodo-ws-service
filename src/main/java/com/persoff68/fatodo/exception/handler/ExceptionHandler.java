package com.persoff68.fatodo.exception.handler;

import com.persoff68.fatodo.exception.util.ProblemUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;

@ControllerAdvice
class ExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();
        if (problem instanceof ConstraintViolationProblem) {
            problem = ProblemUtils.processConstraintViolationProblem(problem, request);
        } else if (problem instanceof DefaultProblem) {
            problem = ProblemUtils.processDefaultProblem(problem, request);
        }
        return new ResponseEntity<>(problem, entity.getHeaders(), entity.getStatusCode());
    }

}
