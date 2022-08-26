package com.persoff68.fatodo.client.configuration;

import com.persoff68.fatodo.exception.ClientException;
import com.persoff68.fatodo.model.ItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemServiceClientWrapper implements ItemServiceClient {

    @Qualifier("feignItemServiceClient")
    private final ItemServiceClient itemServiceClient;


    @Override
    public List<ItemInfo> getAllItemInfoByIds(List<UUID> itemIdList) {
        try {
            return itemServiceClient.getAllItemInfoByIds(itemIdList);
        } catch (Exception e) {
            throw new ClientException();
        }
    }
}
