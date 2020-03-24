package com.persoff68.fatodo.exception.attribute.strategy;

import com.persoff68.fatodo.exception.attribute.util.AttributeUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

public final class ExceptionAttributeStrategy extends AbstractAttributeStrategy {
    private static final String REQUEST_URI_PATH = "javax.servlet.error.request_uri";

    private final HttpServletRequest request;

    public ExceptionAttributeStrategy(HttpServletRequest request, Exception exception) {
        super(exception);
        this.request = request;
    }

    @Override
    public void addPath() {
        WebRequest webRequest = new ServletWebRequest(request);
        String path = AttributeUtils.getAttribute(webRequest, REQUEST_URI_PATH);
        path = path == null ? request.getRequestURI() : path;
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

}
