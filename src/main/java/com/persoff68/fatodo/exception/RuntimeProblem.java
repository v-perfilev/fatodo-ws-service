package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class RuntimeProblem extends AbstractThrowableProblem {

    public RuntimeProblem(Throwable throwable) {
        super(ExceptionTypes.RUNTIME_TYPE, throwable.getCause().getClass().getSimpleName(),
                Status.INTERNAL_SERVER_ERROR, throwable.getCause().getMessage());
    }

}
