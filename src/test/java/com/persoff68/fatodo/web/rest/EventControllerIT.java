package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.persoff68.fatodo.FatodoWsServiceApplication;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestChat;
import com.persoff68.fatodo.builder.TestChatMessage;
import com.persoff68.fatodo.builder.TestChatReaction;
import com.persoff68.fatodo.builder.TestComment;
import com.persoff68.fatodo.builder.TestCommentReaction;
import com.persoff68.fatodo.builder.TestContactRequest;
import com.persoff68.fatodo.builder.TestItem;
import com.persoff68.fatodo.builder.TestItemGroup;
import com.persoff68.fatodo.builder.TestItemGroupMember;
import com.persoff68.fatodo.builder.TestItemInfo;
import com.persoff68.fatodo.builder.TestReminder;
import com.persoff68.fatodo.builder.TestUserInfo;
import com.persoff68.fatodo.builder.TestWsEvent;
import com.persoff68.fatodo.client.ItemServiceClient;
import com.persoff68.fatodo.client.UserServiceClient;
import com.persoff68.fatodo.model.ItemInfo;
import com.persoff68.fatodo.model.UserInfo;
import com.persoff68.fatodo.model.WsEvent;
import com.persoff68.fatodo.model.constant.WsEventType;
import com.persoff68.fatodo.model.event.Chat;
import com.persoff68.fatodo.model.event.ChatMessage;
import com.persoff68.fatodo.model.event.ChatReaction;
import com.persoff68.fatodo.model.event.Comment;
import com.persoff68.fatodo.model.event.CommentReaction;
import com.persoff68.fatodo.model.event.ContactRequest;
import com.persoff68.fatodo.model.event.Item;
import com.persoff68.fatodo.model.event.ItemGroup;
import com.persoff68.fatodo.model.event.ItemGroupMember;
import com.persoff68.fatodo.model.event.Reminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoWsServiceApplication.class)
@AutoConfigureMockMvc
class EventControllerIT {
    private static final String ENDPOINT = "/api/event";

    private static final UUID ACTIVE_USER_ID = UUID.randomUUID();
    private static final String ACTIVE_USER_NAME = "active";
    private static final UUID INACTIVE_USER_ID = UUID.randomUUID();
    private static final String INACTIVE_USER_NAME = "inactive";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

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
        UserInfo activeUserInfo = TestUserInfo.defaultBuilder()
                .id(ACTIVE_USER_ID).username(ACTIVE_USER_NAME).build().toParent();
        UserInfo inactiveUserInfo = TestUserInfo.defaultBuilder()
                .id(INACTIVE_USER_ID).username(INACTIVE_USER_NAME).language("ru").build().toParent();
        when(userServiceClient.getAllUserInfoByIds(List.of(ACTIVE_USER_ID)))
                .thenReturn(List.of(activeUserInfo));
        when(userServiceClient.getAllUserInfoByIds(List.of(INACTIVE_USER_ID)))
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

    @Test
    @WithCustomSecurityContext
    void testSendItemCreateEvent_ok() throws Exception {
        Item item = TestItem.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(item);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.ITEM_CREATE).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendItemGroupCreateEvent_ok() throws Exception {
        ItemGroup group = TestItemGroup.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(group);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.ITEM_GROUP_CREATE).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendItemMemberAddEvent_ok() throws Exception {
        ItemGroupMember member = TestItemGroupMember.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(List.of(member));
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.ITEM_MEMBER_ADD).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendChatCreateEvent_ok() throws Exception {
        Chat chat = TestChat.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(chat);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.CHAT_CREATE).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendChatMessageCreateEvent_ok() throws Exception {
        ChatMessage message = TestChatMessage.defaultBuilder().userId(ACTIVE_USER_ID).build().toParent();
        String payload = objectMapper.writeValueAsString(message);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.CHAT_MESSAGE_CREATE).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendChatReactionIncomingEvent_ok() throws Exception {
        ChatReaction reaction = TestChatReaction.defaultBuilder().userId(ACTIVE_USER_ID).build().toParent();
        String payload = objectMapper.writeValueAsString(reaction);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.CHAT_REACTION_INCOMING).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendContactRequestIncomingEvent_ok() throws Exception {
        ContactRequest contactRequest = TestContactRequest.defaultBuilder()
                .requesterId(ACTIVE_USER_ID).build().toParent();
        String payload = objectMapper.writeValueAsString(contactRequest);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.CONTACT_REQUEST_INCOMING).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendContactAcceptIncomingEvent_ok() throws Exception {
        ContactRequest contactRequest = TestContactRequest.defaultBuilder()
                .recipientId(ACTIVE_USER_ID).build().toParent();
        String payload = objectMapper.writeValueAsString(contactRequest);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.CONTACT_ACCEPT_INCOMING).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendCommentCreateEvent_ok() throws Exception {
        Comment comment = TestComment.defaultBuilder().userId(ACTIVE_USER_ID).build().toParent();
        String payload = objectMapper.writeValueAsString(comment);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.COMMENT_CREATE).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendCommentReactionIncomingEvent_ok() throws Exception {
        CommentReaction reaction = TestCommentReaction.defaultBuilder().userId(ACTIVE_USER_ID).build().toParent();
        String payload = objectMapper.writeValueAsString(reaction);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.COMMENT_REACTION_INCOMING).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomSecurityContext
    void testSendReminderEvent_ok() throws Exception {
        Reminder reminder = TestReminder.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(reminder);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.REMINDER).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }


    @Test
    @WithAnonymousUser
    void testSendEvent_unauthorized() throws Exception {
        Item item = TestItem.defaultBuilder().build().toParent();
        String payload = objectMapper.writeValueAsString(item);
        WsEvent event = TestWsEvent.defaultBuilder()
                .type(WsEventType.ITEM_CREATE).payload(payload).build().toParent();
        String requestBody = objectMapper.writeValueAsString(event);
        mvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

}
