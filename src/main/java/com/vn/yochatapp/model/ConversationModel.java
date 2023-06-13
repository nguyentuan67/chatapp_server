package com.vn.yochatapp.model;

import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.entities.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConversationModel {
    private Long id;
    private int type;
    private Long channelId;
    private String name;
    private String avtarUrl;
    private List<Message> messages;
    private List<AuthUserModel> users;

    public ConversationModel(Conversation conv) {
        this.id = conv.getId();
        this.type = conv.getType();
        this.channelId = conv.getChannelId();
        this.name = conv.getName();
        this.avtarUrl = conv.getAvatarUrl();
        this.messages = null;
    }
}