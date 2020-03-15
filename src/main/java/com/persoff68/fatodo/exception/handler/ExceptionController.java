package com.persoff68.fatodo.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.attribute.AttributeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ExceptionController implements ErrorController {
    private final static String ERROR_PATH = "/error";

    private final ObjectMapper objectMapper;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> error(HttpServletRequest request) throws IOException {
        return AttributeHandler.from(request).getResponseEntity(objectMapper);
    }

}
