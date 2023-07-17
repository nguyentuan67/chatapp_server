package com.vn.yochatapp.service;

import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.entities.Message;

import java.util.List;

public interface ConversationService {

    Conversation findById(Long id);
    Conversation findConversationByUserId(Long authUserId, Long userId);
    List<Message> findMessages(Long convId, int offset, int size);
    List<Conversation> getConversations(Long userId);
    void create(Conversation conv);
}
