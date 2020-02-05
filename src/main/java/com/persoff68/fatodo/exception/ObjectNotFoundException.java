package com.persoff68.fatodo.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ObjectNotFoundException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException() {
        super(null, "Object not found in database", Status.NOT_FOUND);
    }

}
