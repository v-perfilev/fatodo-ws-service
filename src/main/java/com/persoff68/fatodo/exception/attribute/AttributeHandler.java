package com.persoff68.fatodo.exception.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AttributeHandler {

    private final attributeStrategy attributeStrategy;

    private AttributeHandler(HttpServletRequest request) {
        this.attributeStrategy = new RequestAttributeStrategy(request);
    }

    private AttributeHandler(HttpServletRequest request, Exception exception) {
        this.attributeStrategy = new ExceptionAttributeStrategy(request, exception);
    }

    public static AttributeHandler from(HttpServletRequest request) {
        return new AttributeHandler(request);
    }

    public static AttributeHandler from(HttpServletRequest request, Exception exception) {
        return new AttributeHandler(request, exception);
    }

    public ResponseEntity<String> getResponseEntity(ObjectMapper objectMapper) throws IOException {
        HttpStatus status = getStatus();
        Map<String, Object> errorAttributes = getErrorAttributes();
        String responseBody = objectMapper.writeValueAsString(errorAttributes);
        return ResponseEntity.status(status).body(responseBody);
    }

    public void sendError(ObjectMapper objectMapper, HttpServletResponse response) throws IOException {
        HttpStatus status = getStatus();
        Map<String, Object> errorAttributes = getErrorAttributes();
        String responseBody = objectMapper.writeValueAsString(errorAttributes);
        response.sendError(status.value(), responseBody);
    }

    private Map<String, Object> getErrorAttributes() {
        attributeStrategy.addTimestamp();
        attributeStrategy.addStatus();
        attributeStrategy.addErrorDetails();
        attributeStrategy.addPath();
        return attributeStrategy.getErrorAttributes();
    }

    private HttpStatus getStatus() {
        return attributeStrategy.getStatus();
    }
}
