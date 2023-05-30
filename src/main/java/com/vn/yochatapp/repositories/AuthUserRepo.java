package com.vn.yochatapp.repositories;

import com.vn.yochatapp.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "authUserRepo")
public interface AuthUserRepo extends JpaRepository<AuthUser, Long> {
    AuthUser findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value = "SELECT au" +
            " FROM AuthUser au WHERE (au.username LIKE concat('%',:name,'%')" +
            " or au.fullName LIKE concat('%',:name,'%'))" +
            " and au.id <> :id")
    List<AuthUser> searchUserByNameAndNotId(@Param(value = "name") String name, @Param(value = "id") Long id);
}
