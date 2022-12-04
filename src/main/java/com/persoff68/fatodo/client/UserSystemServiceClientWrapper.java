package com.persoff68.fatodo.client;

import com.persoff68.fatodo.exception.ClientException;
import com.persoff68.fatodo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserSystemServiceClientWrapper implements UserSystemServiceClient {

    @Qualifier("feignUserSystemServiceClient")
    private final UserSystemServiceClient userSystemServiceClient;

    @Override
    public List<User> getAllUserDataByIds(@RequestParam("ids") List<UUID> userIdList) {
        try {
            return userSystemServiceClient.getAllUserDataByIds(userIdList);
        } catch (Exception e) {
            throw new ClientException();
        }
    }

}
