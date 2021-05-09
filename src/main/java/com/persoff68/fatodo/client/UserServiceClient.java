package com.persoff68.fatodo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service", primary = false)
public interface UserServiceClient {

    @PostMapping(value = "/api/user-data/usernames/ids")
    List<String> getAllUsernamesByIds(@RequestBody List<UUID> userIdList);

}

