package com.vn.yochatapp.service;

import com.vn.yochatapp.entities.AuthUser;

public interface AuthUserService {
    AuthUser findOne(Long id);

    AuthUser findByUsername(String username);

    Boolean existsByUsername(String username);

    void create(AuthUser authUser);

    void update(AuthUser authUser);

    void delete(AuthUser authUser);
}
