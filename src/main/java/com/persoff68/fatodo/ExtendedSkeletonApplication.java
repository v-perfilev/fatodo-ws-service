package com.persoff68.fatodo;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.util.ProfileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Properties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ExtendedSkeletonApplication {

    public static void main(String[] args) {
        Properties defaultProfileProperties = ProfileUtils.getDefaultProfile();
        SpringApplication app = new SpringApplication(ExtendedSkeletonApplication.class);
        app.setDefaultProperties(defaultProfileProperties);
        app.run(args);
    }

}
