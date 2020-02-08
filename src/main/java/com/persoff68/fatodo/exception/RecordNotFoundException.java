package com.persoff68.fatodo.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class RecordNotFoundException extends AbstractThrowableProblem {

    public RecordNotFoundException() {
        super(null, "Record not found in database", Status.NOT_FOUND);
    }

}
