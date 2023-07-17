package com.vn.yochatapp.service.impl;

import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.entities.Message;
import com.vn.yochatapp.repositories.ConversationRepo;
import com.vn.yochatapp.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    ConversationRepo conversationRepo;


    @Override
    public Conversation findById(Long id) {
        return conversationRepo.getConversationById(id);
    }

    @Override
    public Conversation findConversationByUserId(Long authUserId, Long userId) {
        return conversationRepo.findConversationByUserId(authUserId, userId);
    }

    @Override
    public List<Message> findMessages(Long convId, int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size, Sort.by("time").descending());
        return conversationRepo.findMessagesByConversationId(convId, pageable).getContent();
    }

    @Override
    public List<Conversation> getConversations(Long userId) {
        return conversationRepo.getConversations(userId);
    }

    @Override
    public void create(Conversation conv) {
        conversationRepo.save(conv);
    }
}
