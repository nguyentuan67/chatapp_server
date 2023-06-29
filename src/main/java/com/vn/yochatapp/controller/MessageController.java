package com.vn.yochatapp.controller;

import com.vn.yochatapp.Constants;
import com.vn.yochatapp.entities.Message;
import com.vn.yochatapp.model.CommonResponse;
import com.vn.yochatapp.model.MessageRequest;
import com.vn.yochatapp.service.AuthUserService;
import com.vn.yochatapp.service.ConversationService;
import com.vn.yochatapp.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    AuthUserService authUserService;

    @Autowired
    ConversationService conversationService;

    @PostMapping("/add")
    public CommonResponse addMessage(@RequestBody MessageRequest request) {
        CommonResponse response = new CommonResponse<>();
        try {
            Message message = new Message();
            message.setTime(new Date());
            message.setContent(request.getContent());
            message.setAuthUser(authUserService.findOne(request.getUserId()));
            message.setConversation(conversationService.findById(request.getConversationId()));
            messageService.create(message);

            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("Thành công");
            response.setOutput(null);
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
}
