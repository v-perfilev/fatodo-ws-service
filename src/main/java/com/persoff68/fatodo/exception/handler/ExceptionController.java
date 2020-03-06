package com.persoff68.fatodo.exception.handler;

import com.persoff68.fatodo.exception.InheritedProblem;
import com.persoff68.fatodo.exception.InternalServerProblem;
import com.persoff68.fatodo.exception.PageNotFoundProblem;
import com.persoff68.fatodo.exception.RuntimeProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

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
        Integer statusCodeInteger = (Integer) request.getAttribute(JAVAX_STATUS_CODE);
        int statusCode = statusCodeInteger != null ? statusCodeInteger : 500;
        Exception exception = (Exception) request.getAttribute(JAVAX_EXCEPTION);
        ThrowableProblem problem = exception != null ? handleException(exception, statusCode) : handleStatus(statusCode);
        return exceptionTranslator.create(problem, new ServletWebRequest(request));
    }

    private ThrowableProblem handleException(Exception e, Integer statusCode) {
        if (e instanceof ThrowableProblem) {
            return (ThrowableProblem) e;
        } else if (e instanceof RuntimeException) {
            return new RuntimeProblem(e, statusCode);
        } else {
            return new InheritedProblem(e, statusCode);
        }
    }

    private ThrowableProblem handleStatus(int statusCode) {
        if (statusCode == 404) {
            return new PageNotFoundProblem();
        } else {
            return new InternalServerProblem();
        }
    }
}
