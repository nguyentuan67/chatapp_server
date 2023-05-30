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
    private String username;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private List<Role> roles;

    public AuthUserModel(AuthUser authUser) {
        this.username = authUser.getUsername();
        this.firstName = authUser.getFirstName();
        this.lastName = authUser.getLastName();
        for(Role role : authUser.getAuthRoles()) {
            this.roles.add(role);
        }
    }
}
