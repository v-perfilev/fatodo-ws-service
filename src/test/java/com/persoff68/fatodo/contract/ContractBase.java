package com.persoff68.fatodo.contract;

import com.persoff68.fatodo.builder.TestUserInfo;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.UserInfo;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
class ContractBase {

    @Autowired
    WebApplicationContext context;

    @MockBean
    UserServiceClient userServiceClient;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);

        UserInfo userInfo = TestUserInfo.defaultBuilder().build().toParent();
        List<UserInfo> userInfoList = List.of(userInfo);
        when(userServiceClient.getAllUserInfoByIds(any())).thenReturn(userInfoList);
    }

}
