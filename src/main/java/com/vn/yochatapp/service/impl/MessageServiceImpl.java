package com.vn.yochatapp.service.impl;

import com.vn.yochatapp.entities.Message;
import com.vn.yochatapp.repositories.MessageRepo;
import com.vn.yochatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepo messageRepo;

    @Override
    public void create(Message message) {
        messageRepo.save(message);
    }
}
