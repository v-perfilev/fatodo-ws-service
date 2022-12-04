package com.persoff68.fatodo.config;

import com.persoff68.fatodo.client.ItemSystemServiceClient;
import com.persoff68.fatodo.client.UserSystemServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final BeanFactory beanFactory;

    @Bean
    @Primary
    public ItemSystemServiceClient itemClient() {
        return (ItemSystemServiceClient) beanFactory.getBean("itemSystemServiceClientWrapper");
    }

    @Bean
    @Primary
    public UserSystemServiceClient userClient() {
        return (UserSystemServiceClient) beanFactory.getBean("userSystemServiceClientWrapper");
    }


}
