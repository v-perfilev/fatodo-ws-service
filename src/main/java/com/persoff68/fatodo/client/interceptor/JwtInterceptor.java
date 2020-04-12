package com.persoff68.fatodo.client.interceptor;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.security.jwt.JwtTokenProvider;
import com.persoff68.fatodo.security.util.SecurityUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements RequestInterceptor {

    private final AppProperties appProperties;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String jwt = SecurityUtils.getCurrentJwt()
                .orElse(jwtTokenProvider.createSystemJwt());
        String header = appProperties.getAuth().getAuthorizationHeader();
        String value = appProperties.getAuth().getAuthorizationPrefix() + " " + jwt;
        requestTemplate.header(header, value);
    }
}
