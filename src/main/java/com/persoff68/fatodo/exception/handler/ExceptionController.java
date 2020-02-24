package com.persoff68.fatodo.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

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
        Integer statusCode = (Integer) request.getAttribute(JAVAX_STATUS_CODE);
        statusCode = statusCode != null ? statusCode : 500;
        Status status = Status.valueOf(statusCode);
        Exception exception = (Exception) request.getAttribute(JAVAX_EXCEPTION);
        exception = exception != null ? exception : new UndefinedException(statusCode);
        NativeWebRequest nativeWebRequest = new ServletWebRequest(request);
        return exceptionTranslator.create(status, exception, nativeWebRequest);
    }

    private static class UndefinedException extends Exception {

        public UndefinedException(int status) {
            super(getMessage(status));
        }

        private static String getMessage(int status) {
            String msg;
            if (status == 404) {
                msg = "Page not found";
            } else {
                msg = "Internal server error";
            }
            return msg;
        }
    }

}
