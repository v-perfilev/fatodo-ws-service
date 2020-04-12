package com.persoff68.fatodo.config;

import com.persoff68.fatodo.config.aop.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfiguration {

    @Bean
    LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

}
