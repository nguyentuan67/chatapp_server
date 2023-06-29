package com.vn.yochatapp.model;

import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.entities.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class MessageModel {
    private Long id;
    private String content;
    private AuthUserModel user;
    private Date time;
    private ConversationModel conversation;

    public MessageModel(Message message) {
        this.id = message.getId();;
        this.content = message.getContent();
        this.user = new AuthUserModel(message.getAuthUser());
        this.time = message.getTime();
        this.conversation = new ConversationModel(message.getConversation());
    }
}
