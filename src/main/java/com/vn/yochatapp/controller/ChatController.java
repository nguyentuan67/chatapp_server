package com.vn.yochatapp.controller;

import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.entities.Message;
import com.vn.yochatapp.entities.Participants;
import com.vn.yochatapp.model.MessageModel;
import com.vn.yochatapp.model.MessageRequest;
import com.vn.yochatapp.service.AuthUserService;
import com.vn.yochatapp.service.ConversationService;
import com.vn.yochatapp.service.MessageService;
import com.vn.yochatapp.service.ParticipantsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    ConversationService conversationService;

    @Autowired
    AuthUserService authUserService;

    @Autowired
    MessageService messageService;

    @Autowired
    ParticipantsService participantsService;
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/user/{userId}")
    public void sendMessage(@DestinationVariable Long userId, MessageRequest messageRequest) throws Exception {
        try {
            // Xử lý tin nhắn riêng tư
            AuthUser authUser = authUserService.findOne(messageRequest.getUserId());
            Message message = new Message();

            message.setConversation(conversationService.findById(messageRequest.getConversationId()));
            message.setContent(messageRequest.getContent());
            message.setAuthUser(authUser);
            message.setTime(new Date());
            messageService.create(message);

            // Gửi lại tin nhắn tới người dùng đích sử dụng SimpMessagingTemplate
            messagingTemplate.convertAndSend( "/topic/user/"+userId, new MessageModel(message));
            messagingTemplate.convertAndSend( "/topic/user/"+messageRequest.getUserId(), new MessageModel(message));
        } catch (Exception e) {
            logger.error("send message", e);
        }
    }
}
