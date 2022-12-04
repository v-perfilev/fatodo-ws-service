package com.persoff68.fatodo.contract;

import com.google.firebase.messaging.FirebaseMessaging;
import com.persoff68.fatodo.client.ItemSystemServiceClient;
import com.persoff68.fatodo.model.ItemInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    ItemSystemServiceClient itemSystemServiceClient;
    @MockBean
    FirebaseMessaging firebaseMessaging;

    @Test
    void testGetAllItemInfoByIds() {
        List<UUID> idList = Collections.singletonList(UUID.randomUUID());
        List<ItemInfo> itemInfoList = itemSystemServiceClient.getAllItemInfoByIds(idList);
        assertThat(itemInfoList).isNotEmpty();
    }

}
