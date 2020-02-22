package com.persoff68.fatodo.config;

import com.persoff68.fatodo.config.constant.AppConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = AppConstants.FEIGN_CLIENT_PATH)
public class CloudConfiguration {
}
