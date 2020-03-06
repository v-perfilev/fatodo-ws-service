package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UndefinedProblem extends AbstractThrowableProblem {

    public UndefinedProblem(int statusCode) {
        super(ExceptionTypes.DEFAULT_TYPE, getMessage(statusCode), Status.valueOf(statusCode));
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
