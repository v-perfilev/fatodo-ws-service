package com.persoff68.fatodo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseService {

    public void sendMessages(List<UUID> userIdList, String payload) {
        System.out.println(userIdList);
        System.out.println(payload);
    }

}
