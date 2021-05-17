package com.example.spring.database;

import com.example.spring.model.Tutor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("sql_dao")
public class MySQL_DAO implements DAO {

    private static Connection con = DatabaseConnection.getConnection();


    @Override
    public int insertTutor(Tutor tutor) throws SQLException {
        if (tutor == null) {
            return -1;
        }
        tutor.setId();

        String query = "INSERT INTO tutors(uuid, firstname, lastname, email, imageURL) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, tutor.getId().toString());
        ps.setString(2, tutor.getFirstName());
        ps.setString(3, tutor.getSurname());
        ps.setString(4, tutor.getEmail());
        ps.setString(5, tutor.getImagePath());

        List<String> subjects = tutor.getSubjects();

        for (String subject : subjects) {
            String query2 = "INSERT INTO tutors_subjects(tutorid, subject) VALUES (?, ?)";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setString(1, tutor.getId().toString());
            ps2.setString(2, subject);

            ps2.executeUpdate();
        }

        return ps.executeUpdate();
    }

    @Override
    public List<Tutor> selectAllTutors(int fromIndex, int numberOfTutors, String[] filters) throws SQLException, IOException {
        int filterCount = filters.length;

        String query;
        PreparedStatement ps;

        if (filterCount > 0) {
            StringBuilder querySB = new StringBuilder();
            querySB.append("SELECT uuid, firstname, lastname, email,imageURL FROM tutors_subjects " +
                    "JOIN tutors ON tutors.uuid = tutors_subjects.tutorid WHERE subject IN(");

            String list = "?, ".repeat(filterCount);
            list = list.replaceAll(", $", "");
            querySB.append(list);

            querySB.append(") GROUP BY tutors_subjects.tutorid HAVING COUNT(tutorid) = ? LIMIT ? OFFSET ?;");
            query = querySB.toString();

            ps = con.prepareStatement(query);

            for (int i = 0; i < filterCount; i++) {
                ps.setString(i + 1, filters[i]);
            }

            ps.setInt(filterCount + 1, filterCount);
            ps.setInt(filterCount + 2, numberOfTutors);
            ps.setInt(filterCount + 3, fromIndex);

        } else {
            query = "SELECT * FROM tutors LIMIT ? OFFSET ?;";
            ps = con.prepareStatement(query);

            ps.setInt(1, numberOfTutors);
            ps.setInt(2, fromIndex);
        }
//
//        System.out.println(query);
//        System.out.println("PrepState: " + ps);

        ResultSet rs = ps.executeQuery();

        List<Tutor> tutors = new ArrayList<>();

        while (rs.next()) {
            List<String> subjects = new ArrayList<>();

            String subjectQuery = "SELECT * FROM tutors_subjects WHERE tutorid = ?";
            String idString = rs.getString("uuid");
            PreparedStatement ps2 = con.prepareStatement(subjectQuery);
            ps2.setString(1, idString);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                subjects.add(rs2.getString("subject"));
            }

            UUID uuid = UUID.fromString(idString);
            String firstName = rs.getString("firstname");
            String surname = rs.getString("lastname");
            String email = rs.getString("email");
            String imageURL = rs.getString("imageURL");

            Tutor tutor = new Tutor(firstName, surname, email, null, null);
            tutor.addSubjects(subjects);
            tutor.setId(uuid);
            tutor.setImagePath(imageURL);

            tutors.add(tutor);
        }

        return tutors;
    }

    @Override
    public int removeTutor(UUID uid) throws SQLException {
        String query = "DELETE FROM tutors WHERE uuid = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, uid.toString());

        return ps.executeUpdate();
    }
}
