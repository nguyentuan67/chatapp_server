package com.vn.yochatapp.service.impl;

import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.repositories.ChatRepo;
import com.vn.yochatapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatRepo chatRepo;


    @Override
    public Conversation findConversationByUserId(Long authUserId, Long userId) {
        return chatRepo.findConversationByUserId(authUserId, userId);
    }
}
