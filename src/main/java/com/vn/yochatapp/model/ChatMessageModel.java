package com.vn.yochatapp.model;

import lombok.Data;

@Data
public class ChatMessageModel {
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    private MessageType type;
    private String content;
    private String sender;
}
