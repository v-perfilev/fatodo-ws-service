package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoWsServiceApplication;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestWsChatEvent;
import com.persoff68.fatodo.builder.TestWsChatMessageEvent;
import com.persoff68.fatodo.builder.TestWsChatReactionsEvent;
import com.persoff68.fatodo.builder.TestWsChatStatusesEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.WsChatEvent;
import com.persoff68.fatodo.model.WsChatMessageEvent;
import com.persoff68.fatodo.model.WsChatReactionsEvent;
import com.persoff68.fatodo.model.WsChatStatusesEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoWsServiceApplication.class)
public class ChatControllerIT {
    private static final String ENDPOINT = "/api/chat";

    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserServiceClient userServiceClient;

    MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatNewEvent_ok() throws Exception {
        String url = ENDPOINT + "/new";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatNewEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/new";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatUpdateEvent_ok() throws Exception {
        String url = ENDPOINT + "/update";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatUpdateEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/update";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatLastMessageEvent_ok() throws Exception {
        String url = ENDPOINT + "/last-message";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatLastMessageEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/last-message";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatLastMessageUpdateEvent_ok() throws Exception {
        String url = ENDPOINT + "/last-message-update";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatLastMessageUpdateEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/last-message-update";
        WsChatEvent event = TestWsChatEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendMessageNewEvent_ok() throws Exception {
        String url = ENDPOINT + "/message-new";
        WsChatMessageEvent event = TestWsChatMessageEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendMessageNewEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/message-new";
        WsChatMessageEvent event = TestWsChatMessageEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendMessageUpdateEvent_ok() throws Exception {
        String url = ENDPOINT + "/message-update";
        WsChatMessageEvent event = TestWsChatMessageEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendMessageUpdateEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/message-update";
        WsChatMessageEvent event = TestWsChatMessageEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendStatusesEvent_ok() throws Exception {
        String url = ENDPOINT + "/statuses";
        WsChatStatusesEvent event = TestWsChatStatusesEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendStatusesEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/statuses";
        WsChatStatusesEvent event = TestWsChatStatusesEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendReactionsEvent_ok() throws Exception {
        String url = ENDPOINT + "/reactions";
        WsChatReactionsEvent event = TestWsChatReactionsEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendReactionsEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/reactions";
        WsChatReactionsEvent event = TestWsChatReactionsEvent.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

}
