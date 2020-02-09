package com.persoff68.fatodo;

import com.persoff68.fatodo.config.AppProfileUtil;
import com.persoff68.fatodo.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Properties;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableConfigurationProperties(AppProperties.class)
public class ExtendedSkeletonApplication {

    public static void main(String[] args) {
        Properties defaultProfileProperties = AppProfileUtil.getDefaultProfile();
        SpringApplication app = new SpringApplication(ExtendedSkeletonApplication.class);
        app.setDefaultProperties(defaultProfileProperties);
        app.run(args);
    }

}
