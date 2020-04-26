package com.persoff68.fatodo.exception.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.attribute.strategy.AttributeStrategy;
import com.persoff68.fatodo.exception.attribute.strategy.ExceptionAttributeStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AttributeHandler {

    private final AttributeStrategy attributeStrategy;

    private AttributeHandler(HttpServletRequest request, Exception exception) {
        this.attributeStrategy = new ExceptionAttributeStrategy(request, exception);
    }

    public static AttributeHandler from(HttpServletRequest request, Exception exception) {
        return new AttributeHandler(request, exception);
    }

    public Map<String, Object> getErrorAttributes() {
        attributeStrategy.addTimestamp();
        attributeStrategy.addStatus();
        attributeStrategy.addErrorDetails();
        attributeStrategy.addFeedbackCode();
        attributeStrategy.addPath();
        return attributeStrategy.getErrorAttributes();
    }

    public HttpStatus getStatus() {
        return attributeStrategy.getStatus();
    }

    public ResponseEntity<String> getResponseEntity(ObjectMapper objectMapper) throws IOException {
        HttpStatus status = getStatus();
        String body = objectMapper.writeValueAsString(getErrorAttributes());
        return ResponseEntity.status(status).body(body);
    }

    public void sendError(ObjectMapper objectMapper, HttpServletResponse response) throws IOException {
        HttpStatus status = getStatus();
        String body = objectMapper.writeValueAsString(getErrorAttributes());
        response.sendError(status.value(), body);
    }

}
