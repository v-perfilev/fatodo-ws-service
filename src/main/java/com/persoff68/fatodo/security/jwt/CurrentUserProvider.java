package com.persoff68.fatodo.security.jwt;

import com.persoff68.fatodo.security.exception.UnauthorizedException;
import com.persoff68.fatodo.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public String getId() {
        String jwt = SecurityUtils.getCurrentJwt()
                .orElseThrow(UnauthorizedException::new);
        return jwtTokenProvider.getUserIdFromJwt(jwt);
    }

}
