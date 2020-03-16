package com.persoff68.fatodo.exception.attribute.strategy;

import com.persoff68.fatodo.exception.AbstractException;
import com.persoff68.fatodo.exception.attribute.util.AttributeUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public final class ExceptionAttributeStrategy implements AttributeStrategy {
    private static final String REQUEST_URI_PATH = "javax.servlet.error.request_uri";

    @Getter
    protected final Map<String, Object> errorAttributes = new LinkedHashMap<>();

    private final HttpServletRequest request;
    private final Exception exception;

    @Override
    public void addTimestamp() {
        errorAttributes.put("timestamp", new Date());
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

    @Override
    public void addPath() {
        WebRequest webRequest = new ServletWebRequest(request);
        String path = AttributeUtils.getAttribute(webRequest, REQUEST_URI_PATH);
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

}
