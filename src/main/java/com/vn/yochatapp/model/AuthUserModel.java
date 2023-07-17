package com.vn.yochatapp.model;

import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private Boolean gender;
    private byte[] avatar;
    private String avatarUrl;

    public AuthUserModel(AuthUser authUser) {
        this.id = authUser.getId();
        this.username = authUser.getUsername();
        this.firstName = authUser.getFirstName();
        this.lastName = authUser.getLastName();
        this.fullName = authUser.getFullName();
        this.gender = authUser.getGender();
        this.avatar = authUser.getAvatar();
        this.avatarUrl = authUser.getAvatarUrl();
    }
}
