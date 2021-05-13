package com.example.spring.database;

import com.example.spring.model.Tutor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface DAO {

    int insertTutor(Tutor tutor) throws SQLException;
    List<Tutor> selectAllTutors() throws SQLException, IOException;
    int removeTutor(UUID uid) throws SQLException;
}
