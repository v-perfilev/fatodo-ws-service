package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InternalServerProblem extends AbstractThrowableProblem {

    public InternalServerProblem() {
        super(ExceptionTypes.DEFAULT_TYPE, "Internal server error", Status.INTERNAL_SERVER_ERROR);
    }

}
