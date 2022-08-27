package com.persoff68.fatodo.contract;

import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.UserInfo;
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
@AutoConfigureStubRunner(ids = {"com.persoff68.fatodo:userservice:+:stubs"},
        stubsMode = StubRunnerProperties.StubsMode.REMOTE)
class UserServiceCT {

    @Autowired
    UserServiceClient userServiceClient;

    @Test
    void testGetAllUsernamesByIds() {
        List<UUID> idList = Collections.singletonList(UUID.randomUUID());
        List<UserInfo> userInfoList = userServiceClient.getAllUserInfoByIds(idList);
        assertThat(userInfoList).isNotEmpty();
    }

}
