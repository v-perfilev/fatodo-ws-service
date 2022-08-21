package com.persoff68.fatodo.client;

import com.persoff68.fatodo.exception.ClientException;
import com.persoff68.fatodo.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceClientWrapper implements UserServiceClient {

    @Qualifier("feignUserServiceClient")
    private final UserServiceClient userServiceClient;

    @Override
    public List<UserInfo> getAllUserInfoByIds(@RequestParam("ids") List<UUID> userIdList) {
        try {
            return userServiceClient.getAllUserInfoByIds(userIdList);
        } catch (Exception e) {
            throw new ClientException();
        }
    }

}
