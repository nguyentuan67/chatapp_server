package com.vn.yochatapp.controller;

import com.vn.yochatapp.Constants;
import com.vn.yochatapp.config.security.service.UserDetailsImpl;
import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.model.ChatMessageModel;
import com.vn.yochatapp.model.CommonResponse;
import com.vn.yochatapp.model.ConversationModel;
import com.vn.yochatapp.service.AuthUserService;
import com.vn.yochatapp.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    ChatService chatService;

    @Autowired
    AuthUserService authUserService;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("getConversation")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<ConversationModel> getConverstation(@RequestParam("userId") Long userId) {
        CommonResponse<ConversationModel> response = new CommonResponse<>();
        try {
            AuthUser user = authUserService.findOne(userId);
            if (user != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                Conversation conv = chatService.findConversationByUserId(userDetails.getId(), userId);
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
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                response.setMessage(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                response.setOutput(null);
                response.setMessage("Không tồn tại");
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

    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessageModel chatMessage) {
        messagingTemplate.convertAndSend("/topic/ws", chatMessage);
    }
}
