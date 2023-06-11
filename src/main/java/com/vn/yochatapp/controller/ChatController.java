package com.vn.yochatapp.controller;

import com.vn.yochatapp.model.ChatMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessageModel chatMessage) {
        messagingTemplate.convertAndSend("/topic/ws", chatMessage);
    }
}
