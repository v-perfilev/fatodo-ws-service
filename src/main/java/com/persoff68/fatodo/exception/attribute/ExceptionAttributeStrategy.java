package com.persoff68.fatodo.exception.attribute;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public final class ExceptionAttributeStrategy extends AbstractAttributeStrategy {

    private final Exception exception;

    public ExceptionAttributeStrategy(HttpServletRequest request, Exception exception) {
        super(request);
        this.exception = exception;
    }

    @Override
    public HttpStatus getStatus() {
        return exception instanceof AbstractException && ((AbstractException) exception).getStatus() != null
                ? ((AbstractException) exception).getStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public void addStatus() {
        HttpStatus status = getStatus();
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
    }

    @Override
    public void addErrorDetails() {
        String message = exception.getMessage();
        errorAttributes.put("message", message);
    }

}
