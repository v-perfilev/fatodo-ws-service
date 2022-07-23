package com.persoff68.fatodo.client;

import com.persoff68.fatodo.client.configuration.FeignSystemConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service", primary = false,
        configuration = {FeignSystemConfiguration.class},
        qualifiers = {"feignUserServiceClient"})
public interface UserServiceClient {

    @GetMapping(value = "/api/user-data/username")
    List<String> getAllUsernamesByIds(@RequestParam("ids") List<UUID> userIdList);

}

