package com.vn.yochatapp.service.impl;

import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.repositories.AuthUserRepo;
import com.vn.yochatapp.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    AuthUserRepo authUserRepo;

    @Override
    public AuthUser findOne(Long id) {
        return authUserRepo.getOne(id);
    }

    @Override
    public AuthUser findByEmail(String email) {
        return authUserRepo.findByEmail(email);
    }

    @Override
    public AuthUser findByUsername(String username) {
        return authUserRepo.findByUserName(username);
    }
}
