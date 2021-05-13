package com.example.spring.database;

import com.example.spring.model.Tutor;

import java.util.List;
import java.util.UUID;

public class MySQL implements DAO {
    @Override
    public int insertTutor(Tutor tutor) {
        if (tutor == null) {
            return -1;
        }
//      TODO: implement
        return 1;
    }

    @Override
    public List<Tutor> selectAllTutors() {
//      TODO: implement
        return null;
    }

    @Override
    public int removeTutor(UUID uid) {
        //   TODO: implement
        return -1;
    }
}
