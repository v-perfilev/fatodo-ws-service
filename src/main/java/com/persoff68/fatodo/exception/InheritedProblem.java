package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InheritedProblem extends AbstractThrowableProblem {

    public InheritedProblem(Throwable throwable, int statusCode) {
        super(ExceptionTypes.INHERITED_TYPE, throwable.getClass().getSimpleName(), Status.valueOf(statusCode), throwable.getMessage());
    }

}
