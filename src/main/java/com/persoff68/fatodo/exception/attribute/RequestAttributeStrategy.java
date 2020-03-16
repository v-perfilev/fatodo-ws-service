package com.persoff68.fatodo.exception.attribute;

import com.persoff68.fatodo.exception.AbstractException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
public final class RequestAttributeStrategy extends AbstractAttributeStrategy {
    private static final String MESSAGE_PATH = "javax.servlet.error.message";
    private static final String STATUS_CODE_PATH = "javax.servlet.error.status_code";
    private static final String REQUEST_URI_PATH = "javax.servlet.error.request_uri";

    private final HttpServletRequest request;

    @Override
    public HttpStatus getStatus() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        Throwable error = getError(new ServletWebRequest(request));
        Integer statusCode = (Integer) request.getAttribute(STATUS_CODE_PATH);

        if (error instanceof AbstractException) {
            status = ((AbstractException) error).getStatus();
        } else if (statusCode != null) {
            try {
                status = HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                // skip
            }
        }
        return status;
    }

    @Override
    public void addStatus() {
        HttpStatus status = getStatus();
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
    }

    @Override
    public void addErrorDetails() {
        WebRequest webRequest = new ServletWebRequest(request);
        Throwable error = getError(webRequest);
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = error.getCause();
            }
            addErrorMessage(errorAttributes, error);
        }
        Object message = getAttribute(webRequest, MESSAGE_PATH);
        if ((!StringUtils.isEmpty(message) || errorAttributes.get("message") == null)
                && !(error instanceof BindingResult)) {
            errorAttributes.put("message", StringUtils.isEmpty(message) ? "No message available" : message);
        }
    }

    private void addErrorMessage(Map<String, Object> errorAttributes, Throwable error) {
        BindingResult result = extractBindingResult(error);
        if (result == null) {
            errorAttributes.put("message", error.getMessage());
            return;
        }
        if (result.hasErrors()) {
            errorAttributes.put("errors", result.getAllErrors());
            errorAttributes.put("message", "Validation failed for object='" + result.getObjectName()
                    + "'. Error count: " + result.getErrorCount());
        } else {
            errorAttributes.put("message", "No errors");
        }
    }

    private BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        }
        if (error instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) error).getBindingResult();
        }
        return null;
    }

    @Override
    public void addPath() {
        WebRequest webRequest = new ServletWebRequest(request);
        String path = getAttribute(webRequest, REQUEST_URI_PATH);
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

}
