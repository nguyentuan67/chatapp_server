package com.vn.yochatapp.repositories;

import com.vn.yochatapp.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "chatRepo")
public interface ChatRepo extends JpaRepository<Conversation, Long> {

    Conversation getConversationById(Long id);

    @Query(value = "select p.conversation from Participants p " +
            "where p.authUser.id = :authUserId or p.authUser.id = :userId " +
            "group by p.conversation " +
            "having count(p.conversation) = 2 ")
    Conversation findConversationByUserId(@Param("authUserId") Long auId, @Param("userId") Long uId);
}
