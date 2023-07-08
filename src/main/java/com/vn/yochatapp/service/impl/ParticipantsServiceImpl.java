package com.vn.yochatapp.service.impl;

import com.vn.yochatapp.entities.Participants;
import com.vn.yochatapp.repositories.ParticipantsRepo;
import com.vn.yochatapp.service.ParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantsServiceImpl implements ParticipantsService {
    @Autowired
    ParticipantsRepo participantsRepo;
    @Override
    public void create(Participants participants) {
        participantsRepo.save(participants);
    }
}
