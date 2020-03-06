package com.persoff68.fatodo.exception.handler;

import com.persoff68.fatodo.exception.RuntimeProblem;
import com.persoff68.fatodo.exception.UndefinedProblem;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController implements ErrorController {

    private final static String ERROR_PATH = "/error";
    private final static String JAVAX_STATUS_CODE = "javax.servlet.error.status_code";
    private final static String JAVAX_EXCEPTION = "javax.servlet.error.exception";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public void error(HttpServletRequest request) {
        Exception exception = (Exception) request.getAttribute(JAVAX_EXCEPTION);
        Integer statusCode = (Integer) request.getAttribute(JAVAX_STATUS_CODE);
        statusCode = statusCode != null ? statusCode : 500;
        throw handleException(exception, statusCode);
    }

    private ThrowableProblem handleException(Exception e, int statusCode) {
        if (e instanceof RuntimeException) {
            throw new RuntimeProblem(e);
        } else {
            throw new UndefinedProblem(statusCode);
        }
    }

}
