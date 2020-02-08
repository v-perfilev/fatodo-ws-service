package com.persoff68.fatodo.constant;

import java.net.URI;

public interface ExceptionTypeConstants {
    URI DEFAULT_TYPE = URI.create("/problem-with-message");
    URI CONSTRAINT_VIOLATION_TYPE = URI.create("/constraint-violation");
    URI ENTITY_NOT_FOUND_TYPE = URI.create("/entity-not-found");
    URI INVALID_PASSWORD_TYPE = URI.create("/invalid-password");
    URI EMAIL_ALREADY_USED_TYPE = URI.create("/email-already-used");
    URI LOGIN_ALREADY_USED_TYPE = URI.create("/login-already-used");
    URI EMAIL_NOT_FOUND_TYPE = URI.create("/email-not-found");
}
