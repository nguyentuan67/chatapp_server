package com.vn.yochatapp.repositories;

import com.vn.yochatapp.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "messageRepo")
public interface MessageRepo extends JpaRepository<Message, Long> {
}
