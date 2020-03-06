package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.exception.constant.ExceptionTypes;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class PageNotFoundProblem extends AbstractThrowableProblem {

    public PageNotFoundProblem() {
        super(ExceptionTypes.DEFAULT_TYPE, "Page not found", Status.NOT_FOUND);
    }

}
