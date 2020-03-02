package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ModelAlreadyExistsException extends AbstractThrowableProblem {

    public ModelAlreadyExistsException() {
        super(ExceptionTypes.DB_TYPE, "Record already exist in database", Status.BAD_REQUEST);
    }

}
