package com.persoff68.fatodo.config;

import com.persoff68.fatodo.config.constant.AppConstants;
import com.persoff68.fatodo.config.constant.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final Environment environment;

    @Value("${wsBrokerRelay.host:localhost}")
    private String wsBrokerRelayHost;
    @Value("${wsBrokerRelay.port:5672}")
    private String wsBrokerRelayPort;
    @Value("${wsBrokerRelay.login:guest}")
    private String wsBrokerRelayLogin;
    @Value("${wsBrokerRelay.passcode:guest}")
    private String wsBrokerRelayPasscode;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app").setUserDestinationPrefix(AppConstants.WS_USER_PREFIX);

        String[] activeProfiles = environment.getActiveProfiles();

        List<String> brokerRelayProfiles = List.of(
                Profile.DOCKER.getValue(),
                Profile.STAGING.getValue(),
                Profile.PRODUCTION.getValue()
        );

        boolean isBrokerRelayProfile = Arrays.stream(activeProfiles).anyMatch(brokerRelayProfiles::contains);

        if (isBrokerRelayProfile) {
            enableStompBrokerRelay(registry);
        } else {
            enableSimpleBroker(registry);
        }

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    private void enableSimpleBroker(MessageBrokerRegistry registry) {
        log.info("Simple websocket broker used");
        registry.enableSimpleBroker(AppConstants.WS_DESTINATION_PREFIX);
    }

    private void enableStompBrokerRelay(MessageBrokerRegistry registry) {
        log.info("Stomp websocket broker used");
        registry.enableStompBrokerRelay(AppConstants.WS_DESTINATION_PREFIX)
                .setUserDestinationBroadcast(AppConstants.WS_DESTINATION_PREFIX + "destination.broadcast")
                .setUserRegistryBroadcast(AppConstants.WS_DESTINATION_PREFIX + "registry.broadcast")
                .setRelayHost(wsBrokerRelayHost)
                .setRelayPort(Integer.parseInt(wsBrokerRelayPort))
                .setClientLogin(wsBrokerRelayLogin)
                .setClientPasscode(wsBrokerRelayPasscode);
    }

}
