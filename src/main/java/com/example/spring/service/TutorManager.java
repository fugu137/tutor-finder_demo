package com.example.spring.service;

import com.example.spring.database.DAO;
import com.example.spring.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TutorManager {

    private final DAO dao;

    @Autowired
    public TutorManager(@Qualifier("pojo_dao") DAO dao) {
        this.dao = dao;
    }

    public int addTutor(Tutor tutor) {
        return dao.insertTutor(tutor);
    }

    public List<Tutor> getAllTutors() {
        return dao.selectAllTutors();
    }

    public int removeTutor(UUID uid) {
        return dao.removeTutor(uid);
    }
}
