package com.vn.yochatapp.controller;

import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.entities.Message;
import com.vn.yochatapp.model.MessageModel;
import com.vn.yochatapp.model.MessageRequest;
import com.vn.yochatapp.service.AuthUserService;
import com.vn.yochatapp.service.ConversationService;
import com.vn.yochatapp.service.MessageService;
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
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

//    @MessageMapping("/chat")
//    @SendTo("/topic/chat")
//    public MessageRequest sendMessage(@Payload MessageRequest chatMessage) {
//        return chatMessage;
//    }

    @MessageMapping("/chat/user/{userId}")
    public void sendMessage(@DestinationVariable Long userId, MessageRequest messageRequest) throws Exception {
        try {
            // Xử lý tin nhắn riêng tư
            AuthUser authUser = authUserService.findOne(messageRequest.getUserId());

            Message message = new Message();
            message.setConversation(conversationService.findById(messageRequest.getConversationId()));
            message.setContent(messageRequest.getContent());
            message.setAuthUser(authUserService.findOne(authUser.getId()));
            message.setTime(new Date());
            messageService.create(message);

            // Gửi lại tin nhắn tới người dùng đích sử dụng SimpMessagingTemplate
            messagingTemplate.convertAndSend( "/topic/user/"+userId, new MessageModel(message));
        } catch (Exception e) {
            logger.error("send message", e);
        }
    }

//    @MessageMapping("/chat/user/{userId}")
//    public void sendPrivateMessage(@DestinationVariable Long userId, String message) {
//        try {
//            String ReceiverName = authUserService.findOne(userId).getUsername();
//            messagingTemplate.convertAndSendToUser(ReceiverName, "/queue/private-chat", message);
//        } catch (Exception e) {
//            logger.error("send message", e);
//        }
//    }
}
