package com.example.spring.database;

import com.example.spring.model.Tutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("pojo_dao")
public class POJO_DAO implements DAO {

    private static List<Tutor> tutorList = new ArrayList<>();

    @Override
    public int insertTutor(Tutor tutor) {
        if (tutor == null || tutorList == null) {
            return -1;
        }
        tutorList.add(tutor);
        return 1;
    }

    @Override
    public List<Tutor> selectAllTutors() {
        return tutorList;
    }
}
