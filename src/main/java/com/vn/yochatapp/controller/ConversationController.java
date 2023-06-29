package com.vn.yochatapp.controller;

import com.vn.yochatapp.Constants;
import com.vn.yochatapp.config.security.service.UserDetailsImpl;
import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.entities.Message;
import com.vn.yochatapp.model.*;
import com.vn.yochatapp.service.AuthUserService;
import com.vn.yochatapp.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ConversationController {
    private static final Logger logger = LoggerFactory.getLogger(ConversationController.class);

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    ConversationService conversationService;

    @Autowired
    AuthUserService authUserService;

    public ConversationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("getConversation")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<ConversationModel> getConverstation(@RequestParam("userId") Long userId) {
        CommonResponse<ConversationModel> response = new CommonResponse<>();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Conversation conv = conversationService.findConversationByUserId(userDetails.getId(), userId);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("Thành công");
            if (conv != null) {
                //nếu có conv thì tải các tin nhắn trong cuộc hội thoại
                ConversationModel output = new ConversationModel(conv);
                response.setOutput(output);
            } else {
                response.setOutput(null);
            }
            return response;
        } catch (Exception e) {
            logger.error("", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setMessage("Lỗi máy chủ");
            return response;
        }
    }

    @GetMapping("{convId}/message")
    public CommonResponse<List<MessageModel>> getMessage(@PathVariable Long convId, @RequestParam("offset") int offset) {
        CommonResponse<List<MessageModel>> response = new CommonResponse<>();
        try {
            List<Message> messages = conversationService.findMessages(convId, offset, 20);
            if (messages.size() != 0) {
                List<MessageModel> output = new ArrayList<>();
                for (Message message : messages) {
                    output.add(new MessageModel(message));
                }
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setOutput(output);
                response.setError("Thành công");
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setOutput(null);
                response.setError("Đã load hết dữ liệu");
            }
            return response;
        } catch (Exception e) {
            logger.error("", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setMessage("Lỗi máy chủ");
            return response;
        }
    }

    @PostMapping("createPrivateChat")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<Conversation> createPrivateConv() {
        CommonResponse<Conversation> response = new CommonResponse<>();
        try {
            Conversation conv = new Conversation();
            conv.setType(Constants.TypeConversation.PRIVATE);
            conversationService.create(conv);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("Thành công");
            response.setOutput(conv);
            return response;
        } catch (Exception e) {
            logger.error("", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setMessage("Lỗi máy chủ");
            return response;
        }
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessageModel chatMessage) {
        messagingTemplate.convertAndSend("/topic/ws", chatMessage);
    }


}
