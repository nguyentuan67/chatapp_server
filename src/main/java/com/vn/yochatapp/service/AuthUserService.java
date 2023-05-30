package com.vn.yochatapp.service;

import com.vn.yochatapp.entities.AuthUser;

import java.util.List;

public interface AuthUserService {
    AuthUser findOne(Long id);

    AuthUser findByUsername(String username);

    Boolean existsByUsername(String username);

    List<AuthUser> searchUserByNameAnhNotId(String name, Long id);

    void create(AuthUser authUser);

    void update(AuthUser authUser);

    void delete(AuthUser authUser);
}
