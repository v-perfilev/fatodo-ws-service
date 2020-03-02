package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ModelNotFoundException extends AbstractThrowableProblem {

    public ModelNotFoundException() {
        super(ExceptionTypes.DB_TYPE, "Record not found in database", Status.NOT_FOUND);
    }

}
