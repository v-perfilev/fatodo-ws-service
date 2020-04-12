package com.persoff68.fatodo.security.jwt;

import com.persoff68.fatodo.security.util.SecurityUtils;
import com.persoff68.fatodo.service.exception.PermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public String getId() {
        String jwt = SecurityUtils.getCurrentJwt()
                .orElseThrow(PermissionException::new);
        return jwtTokenProvider.getUserIdFromJwt(jwt);
    }

}
