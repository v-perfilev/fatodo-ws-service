package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoWsServiceApplication;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestContactRequest;
import com.persoff68.fatodo.builder.TestUserInfo;
import com.persoff68.fatodo.builder.TestWsEventWithUsers;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.ContactRequest;
import com.persoff68.fatodo.model.UserInfo;
import com.persoff68.fatodo.model.WsEventWithUsers;
import com.persoff68.fatodo.model.constant.WsEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoWsServiceApplication.class)
@AutoConfigureMockMvc
class EventControllerIT {
    private static final String ENDPOINT = "/api/event";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserServiceClient userServiceClient;

    @BeforeEach
    void setup() {
        UserInfo userInfo = TestUserInfo.defaultBuilder().build().toParent();
        List<UserInfo> userInfoList = List.of(userInfo);
        when(userServiceClient.getAllUserInfoByIds(any())).thenReturn(userInfoList);
    }

    @Test
    @WithCustomSecurityContext
    void testSendEvent_ok() throws Exception {
        ContactRequest contactRequest = TestContactRequest.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(contactRequest);
        WsEventWithUsers event = TestWsEventWithUsers.defaultBuilder()
                .type(WsEventType.CONTACT_REQUEST_INCOMING).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendEvent_unauthorized() throws Exception {
        ContactRequest contactRequest = TestContactRequest.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(contactRequest);
        WsEventWithUsers event = TestWsEventWithUsers.defaultBuilder()
                .type(WsEventType.CONTACT_REQUEST_INCOMING).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

}
