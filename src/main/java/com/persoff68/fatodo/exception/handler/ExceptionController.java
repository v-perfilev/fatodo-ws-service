package com.persoff68.fatodo.exception.handler;

import com.persoff68.fatodo.exception.RuntimeProblem;
import com.persoff68.fatodo.exception.UndefinedProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.zalando.problem.Problem;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ExceptionController implements ErrorController {

    private final static String ERROR_PATH = "/error";
    private final static String JAVAX_STATUS_CODE = "javax.servlet.error.status_code";
    private final static String JAVAX_EXCEPTION = "javax.servlet.error.exception";

    private final ExceptionTranslator exceptionTranslator;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Problem> error(HttpServletRequest request) {
        Exception exception = (Exception) request.getAttribute(JAVAX_EXCEPTION);
        Integer statusCode = (Integer) request.getAttribute(JAVAX_STATUS_CODE);
        statusCode = statusCode != null ? statusCode : 500;
        Throwable throwable = handleException(exception, statusCode);
        return exceptionTranslator.create(throwable, new ServletWebRequest(request));
    }

    private Throwable handleException(Exception e, int statusCode) {
        if (e == null) {
            return new UndefinedProblem(statusCode);
        } else if (e instanceof RuntimeException) {
            return new RuntimeProblem(e);
        }
        return e;
    }

}
