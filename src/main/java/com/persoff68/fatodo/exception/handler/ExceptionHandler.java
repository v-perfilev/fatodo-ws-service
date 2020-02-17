package com.persoff68.fatodo.exception.handler;

import com.persoff68.fatodo.exception.util.ProblemUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import javax.annotation.Nullable;

@ControllerAdvice
class ExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();
        problem = ProblemUtils.processProblem(problem, request);
        return new ResponseEntity<>(problem, entity.getHeaders(), entity.getStatusCode());
    }

}
