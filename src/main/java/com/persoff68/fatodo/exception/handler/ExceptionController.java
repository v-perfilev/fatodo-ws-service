package com.persoff68.fatodo.exception.handler;

import com.persoff68.fatodo.exception.attribute.util.AttributeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ExceptionController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> error(HttpServletRequest request) {
        HttpStatus status = AttributeUtils.getStatus(request);
        String body = AttributeUtils.getMessage(new ServletWebRequest(request));
        return ResponseEntity.status(status).body(body);
    }

}
