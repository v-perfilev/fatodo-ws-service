package com.persoff68.fatodo.client;

import com.persoff68.fatodo.client.configuration.FeignSystemConfiguration;
import com.persoff68.fatodo.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service", primary = false,
        configuration = {FeignSystemConfiguration.class},
        qualifiers = {"feignUserServiceClient"})
public interface UserServiceClient {

    @GetMapping(value = "/api/info/user")
    List<UserInfo> getAllUserInfoByIds(@RequestParam("ids") List<UUID> userIdList);

}

