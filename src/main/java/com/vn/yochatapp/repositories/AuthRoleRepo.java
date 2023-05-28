package com.vn.yochatapp.repositories;

import com.vn.yochatapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "authRoleRepo")
public interface AuthRoleRepo extends JpaRepository<Role, Long> {
    @Query(value = " SELECT r FROM Role r WHERE (r.id=:id) ", nativeQuery = false)
    Role getById(@Param(value = "id") Long id);
}
