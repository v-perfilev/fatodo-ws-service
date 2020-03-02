package com.persoff68.fatodo.constant;

import java.net.URI;

public interface ExceptionTypes {
    URI DEFAULT_TYPE = URI.create("/default-error");
    URI DB_TYPE = URI.create("/db-error");
    URI AUTH_TYPE = URI.create("/authentication-error");
    URI VALIDATION_TYPE = URI.create("/validation-error");
}
