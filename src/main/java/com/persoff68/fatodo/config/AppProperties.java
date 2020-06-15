package com.persoff68.fatodo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Common common = new Common();
    private final Auth auth = new Auth();

    @Getter
    @Setter
    public static class Common {
        private String baseUrl;
        private String apiUrl;
    }

    @Getter
    @Setter
    public static class Auth {
        private String authorizationHeader;
        private String authorizationPrefix;
        private String tokenSecret;
        private long tokenExpirationSec;
        private String captchaSecret;
    }
}

