package com.vn.yochatapp.model;

import javax.persistence.Column;
import java.util.Date;

public class ContactModel {
    private String name;
    private int type;
    private byte[] avatar;
    private String avatarUrl;
    private String lastMessage;
    private Date lastContactTime;
}
