package com.vn.yochatapp.repositories;

import com.vn.yochatapp.entities.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "participantsRepo")
public interface ParticipantsRepo extends JpaRepository<Participants, Long> {
}
