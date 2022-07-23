package com.persoff68.fatodo.contract;

import com.persoff68.fatodo.client.UserServiceClient;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

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

        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(Collections.singletonList("test"));
    }

}
