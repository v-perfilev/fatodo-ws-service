package com.persoff68.fatodo.web.rest.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class FormNotValidProblem extends AbstractThrowableProblem {

    public FormNotValidProblem() {
        super(ExceptionTypes.VALIDATION_TYPE, "Form data not valid", Status.BAD_REQUEST);
    }
}
