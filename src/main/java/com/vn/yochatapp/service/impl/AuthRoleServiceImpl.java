package com.vn.yochatapp.service.impl;

import com.vn.yochatapp.entities.Role;
import com.vn.yochatapp.repositories.AuthRoleRepo;
import com.vn.yochatapp.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    AuthRoleRepo authRoleRepo;


    @Override
    public Role findOne(Long id) {
        return authRoleRepo.getById(id);
    }
}
