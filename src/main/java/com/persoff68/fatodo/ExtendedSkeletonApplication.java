package com.persoff68.fatodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {
        UserDetailsServiceAutoConfiguration.class
})
public class ExtendedSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtendedSkeletonApplication.class, args);
    }

}
