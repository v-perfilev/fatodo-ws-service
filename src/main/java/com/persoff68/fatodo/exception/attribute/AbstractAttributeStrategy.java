package com.persoff68.fatodo.exception.attribute;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractAttributeStrategy implements attributeStrategy {
    private static final String EXCEPTION_PATH = "javax.servlet.error.exception";

    @Getter
    protected final Map<String, Object> errorAttributes = new LinkedHashMap<>();

    abstract public void addStatus();

    abstract public void addErrorDetails();

    @Override
    public void addTimestamp() {
        errorAttributes.put("timestamp", new Date());
    }

    protected Throwable getError(WebRequest webRequest) {
        return getAttribute(webRequest, EXCEPTION_PATH);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
