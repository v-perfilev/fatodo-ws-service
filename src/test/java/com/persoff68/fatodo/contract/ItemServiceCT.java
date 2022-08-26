package com.persoff68.fatodo.contract;

import com.persoff68.fatodo.client.ItemServiceClient;
import com.persoff68.fatodo.model.ItemInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureStubRunner(ids = {"com.persoff68.fatodo:itemservice:+:stubs"},
        stubsMode = StubRunnerProperties.StubsMode.REMOTE)
class ItemServiceCT {

    @Autowired
    ItemServiceClient itemServiceClient;

    @Test
    void testGetAllItemInfoByIds() {
        List<UUID> idList = Collections.singletonList(UUID.randomUUID());
        List<ItemInfo> itemInfoList = itemServiceClient.getAllItemInfoByIds(idList);
        assertThat(itemInfoList).isNotEmpty();
    }

}
