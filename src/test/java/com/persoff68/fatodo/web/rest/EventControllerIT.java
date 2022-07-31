package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoWsServiceApplication;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.Event;
import com.persoff68.fatodo.model.EventDelete;
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
        List<String> usernameList = Collections.singletonList("test");
        when(userServiceClient.getAllUsernamesByIds(any())).thenReturn(usernameList);
    }

    @Test
    @WithCustomSecurityContext
    void testSendEvent_ok() throws Exception {
        String url = ENDPOINT + "/new";
        WsEvent<Event> event = TestWsEvent.<Event>defaultBuilder().content(new Event()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendEvent_unauthorized() throws Exception {
        String url = ENDPOINT + "/new";
        WsEvent<Event> event = TestWsEvent.<Event>defaultBuilder().content(new Event()).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithCustomSecurityContext
    void testSendEventDelete_ok() throws Exception {
        String url = ENDPOINT + "/delete";
        WsEvent<EventDelete> event = TestWsEvent.<EventDelete>defaultBuilder().content(new EventDelete())
                .build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testSendEventDelete_unauthorized() throws Exception {
        String url = ENDPOINT + "/delete";
        WsEvent<EventDelete> event = TestWsEvent.<EventDelete>defaultBuilder().content(new EventDelete())
                .build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

}
