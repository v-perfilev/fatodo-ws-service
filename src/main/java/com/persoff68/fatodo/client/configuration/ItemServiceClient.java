package com.persoff68.fatodo.client.configuration;

import com.persoff68.fatodo.model.ItemInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "item-service", contextId = "auth", primary = false,
        configuration = {FeignAuthConfiguration.class},
        qualifiers = {"feignItemServiceClient"})
public interface ItemServiceClient {

    @GetMapping(value = "/api/info/item")
    List<ItemInfo> getAllItemInfoByIds(@RequestParam("ids") List<UUID> itemIdList);

}
