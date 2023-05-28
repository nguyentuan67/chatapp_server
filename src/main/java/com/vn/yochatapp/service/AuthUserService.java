package com.vn.yochatapp.service;

import com.vn.yochatapp.entities.AuthUser;

public interface AuthUserService {
    AuthUser findOne(Long id);

    AuthUser findByEmail(String email);

    AuthUser findByUsername(String username);
}
