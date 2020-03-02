package com.persoff68.fatodo;

import com.persoff68.fatodo.config.AppProfileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class BaseSkeletonApplication {

    public static void main(String[] args) {
        Properties defaultProfileProperties = AppProfileUtils.getDefaultProfile();
        SpringApplication app = new SpringApplication(BaseSkeletonApplication.class);
        app.setDefaultProperties(defaultProfileProperties);
        app.run(args);
    }

}
