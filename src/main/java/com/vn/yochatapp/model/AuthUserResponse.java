package com.vn.yochatapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResponse {
    private Long id;
    private String username;
    private List<String> roles;
    private String jwtToken;
}
