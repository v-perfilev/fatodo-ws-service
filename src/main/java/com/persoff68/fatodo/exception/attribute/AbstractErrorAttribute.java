package com.persoff68.fatodo.exception.attribute;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public abstract class AbstractErrorAttribute {
    private static final String EXCEPTION_PATH = "javax.servlet.error.request_uri";
    private static final String REQUEST_URI_PATH = "javax.servlet.error.request_uri";

    public Throwable getError(WebRequest webRequest) {
        return getAttribute(webRequest, EXCEPTION_PATH);
    }

    protected void addErrorMessage(Map<String, Object> errorAttributes, Throwable error) {
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

    protected BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        }
        if (error instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) error).getBindingResult();
        }
        return null;
    }

    protected void addPath(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        String path = getAttribute(requestAttributes, REQUEST_URI_PATH);
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
