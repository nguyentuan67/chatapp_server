package com.vn.yochatapp.repositories;

import com.vn.yochatapp.entities.Conversation;
import com.vn.yochatapp.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "chatRepo")
public interface ConversationRepo extends JpaRepository<Conversation, Long> {

    Conversation getConversationById(Long id);

    @Query(value = "select p.conversation from Participants p " +
            "where p.authUser.id = :authUserId or p.authUser.id = :userId " +
            "group by p.conversation " +
            "having count(p.conversation) = 2 ")
    Conversation findConversationByUserId(@Param("authUserId") Long auId, @Param("userId") Long uId);

//    @Query("SELECT c " +
//            "FROM Conversation  c " +
//            "LEFT JOIN Message m ON m.id = " +
//            "(SELECT m2.id FROM Message m2 " +
//            "WHERE m2.time = (SELECT max(m3.time) from Message m3 " +
//            "where m3.conversation.id = c.id))" +
//            "INNER JOIN Participants p ON p.conversation.id = c.id " +
//            "WHERE p.authUser.id = :userId " +
//            "order by m.time desc")
    @Query("SELECT c " +
            "FROM Conversation c " +
            "JOIN c.messages m " +
            "JOIN c.participants p " +
            "WHERE p.authUser.id = :userId " +
            "GROUP BY c " +
            "ORDER BY MAX(m.time) DESC")
    List<Conversation> getConversations(@Param("userId") Long userId);
    @Query(value = "SELECT m FROM Message m WHERE m.conversation.id = :convId ORDER BY m.time DESC")
    Page<Message> findMessagesByConversationId(@Param("convId") Long convId, Pageable pageable);
}
