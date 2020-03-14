package com.persoff68.fatodo.exception.attribute;

import com.persoff68.fatodo.exception.AbstractRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ExceptionErrorAttribute extends AbstractErrorAttribute {

    public HttpStatus getStatus(Exception e) {
        return e instanceof AbstractRuntimeException && ((AbstractRuntimeException) e).getStatus() != null
                ? ((AbstractRuntimeException) e).getStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public Map<String, Object> getErrorAttributes(HttpServletRequest request, Exception e) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        addStatus(errorAttributes, e);
        addErrorDetails(errorAttributes, e);
        addPath(errorAttributes, webRequest);
        return errorAttributes;
    }

    private void addStatus(Map<String, Object> errorAttributes, Exception e) {
        HttpStatus status = getStatus(e);
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
    }

    private void addErrorDetails(Map<String, Object> errorAttributes, Exception e) {
        String message = e.getMessage();
        errorAttributes.put("message", message);
    }

}
