package com.vn.yochatapp.service;

import com.vn.yochatapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRoleService {
    Role findOne(Long id);
}
