package com.example.spring.service;

import com.example.spring.database.DAO;
import com.example.spring.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class TutorManager {

    private final DAO dao;

    @Autowired
    public TutorManager(@Qualifier("sql_dao") DAO dao) {
        this.dao = dao;
    }

    public int addTutor(Tutor tutor) throws SQLException {
        return dao.insertTutor(tutor);
    }

    public List<Tutor> getAllTutors(int fromIndex, int toIndex, String[] filters) throws IOException, SQLException {
        return dao.selectAllTutors(fromIndex, toIndex, filters);
    }

    public int removeTutor(UUID uid) throws SQLException {
        return dao.removeTutor(uid);
    }
}
