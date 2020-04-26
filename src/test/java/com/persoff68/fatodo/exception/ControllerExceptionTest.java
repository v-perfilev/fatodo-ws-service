package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.web.rest.exception.InvalidFormException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerExceptionTest {

    @Test
    void testValidationException() {
        Object exception = new InvalidFormException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
