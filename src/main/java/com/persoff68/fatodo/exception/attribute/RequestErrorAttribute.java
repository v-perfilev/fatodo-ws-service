package com.persoff68.fatodo.exception.attribute;

import com.persoff68.fatodo.exception.AbstractRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class RequestErrorAttribute extends AbstractErrorAttribute {
    private static final String MESSAGE_PATH = "javax.servlet.error.message";
    private static final String STATUS_CODE_PATH = "javax.servlet.error.status_code";

    public HttpStatus getStatus(HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        Throwable error = getError(new ServletWebRequest(request));
        Integer statusCode = (Integer) request.getAttribute(STATUS_CODE_PATH);

        if (error instanceof AbstractRuntimeException) {
            status = ((AbstractRuntimeException) error).getStatus();
        } else if (statusCode != null) {
            try {
                status = HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                // skip
            }
        }

        return status;
    }

    public Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        addStatus(errorAttributes, request);
        addErrorDetails(errorAttributes, webRequest);
        addPath(errorAttributes, webRequest);
        return errorAttributes;
    }

    private void addStatus(Map<String, Object> errorAttributes, HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
    }

    private void addErrorDetails(Map<String, Object> errorAttributes, WebRequest webRequest) {
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

}
