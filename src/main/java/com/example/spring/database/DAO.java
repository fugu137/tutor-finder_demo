package com.example.spring.database;

import com.example.spring.model.Tutor;

import java.util.List;
import java.util.UUID;

public interface DAO {

    int insertTutor(Tutor tutor);
    List<Tutor> selectAllTutors();
    int removeTutor(UUID uid);
}
