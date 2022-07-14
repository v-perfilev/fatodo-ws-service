package com.persoff68.fatodo.service.client;

import com.persoff68.fatodo.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserServiceClient userServiceClient;

    public List<String> getAllUsernamesByIds(List<UUID> userIdList) {
        return userServiceClient.getAllUsernamesByIds(userIdList);
    }

}
