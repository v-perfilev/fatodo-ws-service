package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoWsServiceApplication;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.WsEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoWsServiceApplication.class)
@AutoConfigureMockMvc
class ContactControllerIT {
    private static final String ENDPOINT = "/api/contact";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserServiceClient userServiceClient;

    @BeforeEach
    void setup() {
        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    @WithCustomSecurityContext
    void testSendRequestIncomingEvent_ok() throws Exception {
        String url = ENDPOINT + "/request-incoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendRequestIncomingEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/request-incoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithCustomSecurityContext
    void testSendRequestOutcomingEvent_ok() throws Exception {
        String url = ENDPOINT + "/request-outcoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendRequestOutcomingEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/request-outcoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithCustomSecurityContext
    void testSendAcceptIncomingEvent_ok() throws Exception {
        String url = ENDPOINT + "/accept-incoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendAcceptIncomingEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/accept-incoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithCustomSecurityContext
    void testSendAcceptOutcomingEvent_ok() throws Exception {
        String url = ENDPOINT + "/accept-outcoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendAcceptOutcomingEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/accept-outcoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    void testSendDeleteRequestIncomingEvent_ok() throws Exception {
        String url = ENDPOINT + "/delete-request-incoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendDeleteRequestIncomingEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/delete-request-incoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    void testSendDeleteRequestOutcomingEvent_ok() throws Exception {
        String url = ENDPOINT + "/delete-request-outcoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendDeleteRequestOutcomingEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/delete-request-outcoming";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    void testSendDeleteRelationEvent_ok() throws Exception {
        String url = ENDPOINT + "/delete-relation";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendDeleteRelationEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/delete-relation";
        WsEvent<UUID> event = TestWsEvent.<UUID>defaultBuilder().content(UUID.randomUUID()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

}
