package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

public class WrappedRuntimeException extends AbstractThrowableProblem {

    public WrappedRuntimeException(RuntimeException e) {
        super(ExceptionTypes.RUNTIME_TYPE, e.getMessage(), Status.INTERNAL_SERVER_ERROR);
    }
}
