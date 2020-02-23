package com.persoff68.fatodo.exception.translator;

import com.persoff68.fatodo.exception.util.ProblemUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
class ExceptionTranslator implements ErrorController, ProblemHandling, SecurityAdviceTrait {

    private final static String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Problem> error(HttpServletRequest request) {
        NativeWebRequest nativeWebRequest = new ServletWebRequest(request);
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        return create(throwable, nativeWebRequest);
    }

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
