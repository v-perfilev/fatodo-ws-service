package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.ContactRequest;
import lombok.Builder;

import java.util.UUID;

public class TestContactRequest extends ContactRequest {

    @Builder
    TestContactRequest(UUID requesterId, UUID recipientId) {
        super();
        super.setRequesterId(requesterId);
        super.setRecipientId(recipientId);
    }

    public static TestContactRequestBuilder defaultBuilder() {
        return TestContactRequest.builder()
                .requesterId(UUID.randomUUID())
                .recipientId(UUID.randomUUID());
    }

    public ContactRequest toParent() {
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setRequesterId(getRequesterId());
        contactRequest.setRecipientId(getRecipientId());
        return contactRequest;
    }

}
