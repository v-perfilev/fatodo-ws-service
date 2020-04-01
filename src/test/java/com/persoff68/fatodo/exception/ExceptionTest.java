package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.security.exception.ForbiddenException;
import com.persoff68.fatodo.security.exception.UnauthorizedException;
import com.persoff68.fatodo.service.exception.ModelAlreadyExistsException;
import com.persoff68.fatodo.service.exception.ModelDuplicatedException;
import com.persoff68.fatodo.service.exception.ModelNotFoundException;
import com.persoff68.fatodo.web.rest.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionTest {

    // Common exceptions

    @Test
    void testClientException() {
        Object exception = new ClientException(HttpStatus.INTERNAL_SERVER_ERROR, "test_message");
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // Service exceptions

    @Test
    void testModelAlreadyExistsException_firstConstructor() {
        Object exception = new ModelAlreadyExistsException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testModelAlreadyExistsException_secondConstructor() {
        Object exception = new ModelAlreadyExistsException(Object.class);
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testModelDuplicatedException_firstConstructor() {
        Object exception = new ModelDuplicatedException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testModelDuplicatedException_secondConstructor() {
        Object exception = new ModelDuplicatedException(Object.class);
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testModelNotFoundException_firstConstructor() {
        Object exception = new ModelNotFoundException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testModelNotFoundException_secondConstructor() {
        Object exception = new ModelNotFoundException(Object.class);
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    // Controller exceptions

    @Test
    void testValidationException() {
        Object exception = new ValidationException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    // Security exceptions

    @Test
    void testForbiddenException() {
        Object exception = new ForbiddenException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void testUnauthorizedException() {
        Object exception = new UnauthorizedException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}
