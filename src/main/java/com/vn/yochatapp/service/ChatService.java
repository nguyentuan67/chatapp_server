package com.vn.yochatapp.service;

import com.vn.yochatapp.entities.Conversation;

public interface ChatService {
    Conversation findConversationByUserId(Long authUserId, Long userId);
}
