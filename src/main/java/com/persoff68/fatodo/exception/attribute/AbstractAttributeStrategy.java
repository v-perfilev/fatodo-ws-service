package com.persoff68.fatodo.exception.attribute;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractAttributeStrategy implements attributeStrategy {
    private static final String EXCEPTION_PATH = "javax.servlet.error.request_uri";
    private static final String REQUEST_URI_PATH = "javax.servlet.error.request_uri";

    @Getter
    protected final Map<String, Object> errorAttributes = new LinkedHashMap<>();
    protected final HttpServletRequest request;

    abstract public void addStatus();

    abstract public void addErrorDetails();

    @Override
    public void addTimestamp() {
        errorAttributes.put("timestamp", new Date());
    }

    @Override
    public void addPath() {
        WebRequest webRequest = new ServletWebRequest(request);
        String path = getAttribute(webRequest, REQUEST_URI_PATH);
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

    protected Throwable getError(WebRequest webRequest) {
        return getAttribute(webRequest, EXCEPTION_PATH);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
