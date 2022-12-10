package com.persoff68.fatodo.client;

import com.persoff68.fatodo.client.configuration.FeignSystemConfiguration;
import com.persoff68.fatodo.model.ItemInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "item-service", primary = false,
        configuration = {FeignSystemConfiguration.class},
        qualifiers = {"feignItemSystemServiceClient"})
public interface ItemSystemServiceClient {

    @GetMapping(value = "/api/system/item")
    List<ItemInfo> getAllItemInfoByIds(@RequestParam("ids") List<UUID> itemIdList);

}
