package com.vn.yochatapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean gender;
}
