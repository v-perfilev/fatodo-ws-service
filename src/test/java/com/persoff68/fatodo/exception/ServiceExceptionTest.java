package com.persoff68.fatodo.exception;

import com.persoff68.fatodo.service.exception.ModelAlreadyExistsException;
import com.persoff68.fatodo.service.exception.ModelDuplicatedException;
import com.persoff68.fatodo.service.exception.ModelInvalidException;
import com.persoff68.fatodo.service.exception.ModelNotFoundException;
import com.persoff68.fatodo.service.exception.PermissionException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceExceptionTest {

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

    @Test
    void testModelInvalidException_firstConstructor() {
        Object exception = new ModelInvalidException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testModelInvalidException_secondConstructor() {
        Object exception = new ModelInvalidException(Object.class);
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPermissionException_firstConstructor() {
        Object exception = new PermissionException();
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPermissionException_secondConstructor() {
        Object exception = new PermissionException("test_message");
        assertThat(exception).isInstanceOf(AbstractException.class);
        AbstractException abstractException = (AbstractException) exception;
        assertThat(abstractException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
