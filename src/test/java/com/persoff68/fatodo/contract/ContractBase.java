package com.persoff68.fatodo.contract;

import com.google.firebase.messaging.FirebaseMessaging;
import com.persoff68.fatodo.builder.TestItemInfo;
import com.persoff68.fatodo.builder.TestUserInfo;
import com.persoff68.fatodo.client.ItemServiceClient;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.ItemInfo;
import com.persoff68.fatodo.model.UserInfo;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
class ContractBase {

    private static final UUID ACTIVE_USER_ID = UUID.randomUUID();
    private static final String ACTIVE_USER_NAME = "active";
    private static final UUID INACTIVE_USER_ID = UUID.randomUUID();
    private static final String INACTIVE_USER_NAME = "inactive";

    @Autowired
    WebApplicationContext context;

    @MockBean
    UserServiceClient userServiceClient;
    @MockBean
    ItemServiceClient itemServiceClient;
    @MockBean
    SimpMessagingTemplate messagingTemplate;
    @MockBean
    SimpUserRegistry userRegistry;
    @MockBean
    FirebaseMessaging firebaseMessaging;


    @BeforeEach
    void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);

        UserInfo activeUserInfo = TestUserInfo.defaultBuilder()
                .id(ACTIVE_USER_ID).username(ACTIVE_USER_NAME).build().toParent();
        UserInfo inactiveUserInfo = TestUserInfo.defaultBuilder()
                .id(INACTIVE_USER_ID).username(INACTIVE_USER_NAME).language("ru").build().toParent();
        when(userServiceClient.getAllUserInfoByIds(eq(List.of(ACTIVE_USER_ID))))
                .thenReturn(List.of(activeUserInfo));
        when(userServiceClient.getAllUserInfoByIds(eq(List.of(INACTIVE_USER_ID))))
                .thenReturn(List.of(inactiveUserInfo));
        when(userServiceClient.getAllUserInfoByIds(any()))
                .thenReturn(List.of(activeUserInfo, inactiveUserInfo));

        ItemInfo itemInfo = TestItemInfo.defaultBuilder().build().toParent();
        when(itemServiceClient.getAllItemInfoByIds(any())).thenReturn(List.of(itemInfo));

        SimpSubscription simpSubscription = Mockito.mock(SimpSubscription.class);
        SimpSession simpSession = Mockito.mock(SimpSession.class);
        SimpUser simpUser = Mockito.mock(SimpUser.class);
        when(simpUser.getName()).thenReturn(ACTIVE_USER_NAME);
        when(simpSession.getUser()).thenReturn(simpUser);
        when(simpSubscription.getSession()).thenReturn(simpSession);
        when(userRegistry.findSubscriptions(any())).thenReturn(Set.of(simpSubscription));
    }

}
