package com.persoff68.fatodo.config.constant;

public interface AppConstants {
    String FEIGN_CLIENT_PATH = "com.persoff68.fatodo.client";
    String SERVICE_PATH = "com.persoff68.fatodo.service";
    String CONTROLLER_PATH = "com.persoff68.fatodo.web.rest";

    String SYSTEM_USERNAME = "system";
    String SYSTEM_AUTHORITY = "ROLE_SYSTEM";
    long SYSTEM_TOKEN_EXPIRATION_SEC = 60;

    long SERIAL_VERSION_UID = 1L;
}
