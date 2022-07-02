package com.persoff68.fatodo.client;

import com.persoff68.fatodo.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceClientWrapper implements UserServiceClient {

    @Qualifier("feignUserServiceClient")
    private final UserServiceClient userServiceClient;

    @Override
    public List<String> getAllUsernamesByIds(List<UUID> userIdList) {
        try {
            return userServiceClient.getAllUsernamesByIds(userIdList);
        } catch (Exception e) {
            throw new ClientException();
        }
    }

}
