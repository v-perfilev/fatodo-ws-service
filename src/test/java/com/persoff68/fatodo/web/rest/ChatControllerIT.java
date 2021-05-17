package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoWsServiceApplication;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.Chat;
import com.persoff68.fatodo.model.ChatMessage;
import com.persoff68.fatodo.model.ChatReactions;
import com.persoff68.fatodo.model.ChatStatuses;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoWsServiceApplication.class)
@AutoConfigureMockMvc
public class ChatControllerIT {
    private static final String ENDPOINT = "/api/chat";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserServiceClient userServiceClient;

    @BeforeEach
    public void setup() {
        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatNewEvent_ok() throws Exception {
        String url = ENDPOINT + "/new";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatNewEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/new";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatUpdateEvent_ok() throws Exception {
        String url = ENDPOINT + "/update";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatUpdateEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/update";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatLastMessageEvent_ok() throws Exception {
        String url = ENDPOINT + "/last-message";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatLastMessageEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/last-message";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendChatLastMessageUpdateEvent_ok() throws Exception {
        String url = ENDPOINT + "/last-message-update";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendChatLastMessageUpdateEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/last-message-update";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendMessageNewEvent_ok() throws Exception {
        String url = ENDPOINT + "/message-new";
        WsEvent<Chat> event = TestWsEvent.<Chat>defaultBuilder().content(new Chat()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendMessageNewEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/message-new";
        WsEvent<ChatMessage> event = TestWsEvent.<ChatMessage>defaultBuilder()
                .content(new ChatMessage()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendMessageUpdateEvent_ok() throws Exception {
        String url = ENDPOINT + "/message-update";
        WsEvent<ChatMessage> event = TestWsEvent.<ChatMessage>defaultBuilder()
                .content(new ChatMessage()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendMessageUpdateEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/message-update";
        WsEvent<ChatMessage> event = TestWsEvent.<ChatMessage>defaultBuilder()
                .content(new ChatMessage()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendStatusesEvent_ok() throws Exception {
        String url = ENDPOINT + "/statuses";
        WsEvent<ChatStatuses> event = TestWsEvent.<ChatStatuses>defaultBuilder()
                .content(new ChatStatuses()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendStatusesEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/statuses";
        WsEvent<ChatStatuses> event = TestWsEvent.<ChatStatuses>defaultBuilder()
                .content(new ChatStatuses()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext
    public void testSendReactionsEvent_ok() throws Exception {
        String url = ENDPOINT + "/reactions";
        WsEvent<ChatReactions> event = TestWsEvent.<ChatReactions>defaultBuilder()
                .content(new ChatReactions()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendReactionsEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/reactions";
        WsEvent<ChatReactions> event = TestWsEvent.<ChatReactions>defaultBuilder()
                .content(new ChatReactions()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

}
