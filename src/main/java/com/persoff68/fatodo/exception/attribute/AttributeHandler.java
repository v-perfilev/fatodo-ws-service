package com.persoff68.fatodo.exception.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AttributeHandler {

    private final ErrorAttributeStrategy errorAttributeStrategy;

    public AttributeHandler(HttpServletRequest request) {
        this.errorAttributeStrategy = new RequestErrorAttributeStrategy(request);
    }

    public AttributeHandler(HttpServletRequest request, Exception exception) {
        this.errorAttributeStrategy = new ExceptionErrorAttributeStrategy(request, exception);
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
        errorAttributeStrategy.addTimestamp();
        errorAttributeStrategy.addStatus();
        errorAttributeStrategy.addErrorDetails();
        errorAttributeStrategy.addPath();
        return errorAttributeStrategy.getErrorAttributes();
    }

    private HttpStatus getStatus() {
        return errorAttributeStrategy.getStatus();
    }
}
