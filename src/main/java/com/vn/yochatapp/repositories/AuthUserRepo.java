package com.vn.yochatapp.repositories;

import com.vn.yochatapp.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "authUserRepo")
public interface AuthUserRepo extends JpaRepository<AuthUser, Long> {
    AuthUser findByUsername(String username);

    Boolean existsByUsername(String username);
}
