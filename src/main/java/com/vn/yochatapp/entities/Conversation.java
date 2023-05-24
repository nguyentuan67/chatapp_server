package com.vn.yochatapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private int type;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "conversation",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany(mappedBy = "conversations")
    private List<AuthUser> authUsers = new ArrayList<AuthUser>();

}
