package com.vn.yochatapp.service.impl;

import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.repositories.AuthUserRepo;
import com.vn.yochatapp.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    AuthUserRepo authUserRepo;

    @Override
    public AuthUser findOne(Long id) {
        return authUserRepo.getAuthUserById(id);
    }

    @Override
    public AuthUser findByUsername(String username) {
        return authUserRepo.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return authUserRepo.existsByUsername(username);
    }

    @Override
    public List<AuthUser> searchUserByNameAnhNotId(String name, Long id) {
        return authUserRepo.searchUserByNameAndNotId(name, id);
    }

    @Override
    public void create(AuthUser authUser) {
        authUserRepo.save(authUser);
    }

    @Override
    public void update(AuthUser authUser) {
        authUserRepo.save(authUser);
    }

    @Override
    public void delete(AuthUser authUser) {
        authUserRepo.delete(authUser);
    }
}
