package com.persoff68.fatodo.web.rest.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class FormNotValidException extends AbstractThrowableProblem {

    public FormNotValidException() {
        super(ExceptionTypes.VALIDATION_TYPE, "Form data not valid", Status.BAD_REQUEST);
    }
}
