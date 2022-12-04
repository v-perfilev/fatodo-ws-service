package com.persoff68.fatodo.client;

import com.persoff68.fatodo.exception.ClientException;
import com.persoff68.fatodo.model.ItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemSystemServiceClientWrapper implements ItemSystemServiceClient {

    @Qualifier("feignItemSystemServiceClient")
    private final ItemSystemServiceClient itemSystemServiceClient;

    @Override
    public List<ItemInfo> getAllItemInfoByIds(List<UUID> itemIdList) {
        try {
            return itemSystemServiceClient.getAllItemInfoByIds(itemIdList);
        } catch (Exception e) {
            throw new ClientException();
        }
    }

}
