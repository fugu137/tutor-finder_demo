package com.example.spring.database;

import com.example.spring.model.Tutor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("sql_dao")
public class MySQL_DAO implements DAO {

    private static Connection con = DatabaseConnection.getConnection();

    public MySQL_DAO() {
        addSampleData();
    }

    //    TODO: remove sample data below
    private void addSampleData() {
        try {
            Tutor emily = new Tutor("Emily", "Falah", "emily.falah@mail.com", null, null);
            List<String> subjects = new ArrayList<>();
            subjects.add("Art");
            subjects.add("Photography");
            subjects.add("English");
            subjects.add("French");
            subjects.add("Arabic");
            emily.addSubjects(subjects);
            emily.setImagePathByName("sample_f1.jpeg");

            Tutor max = new Tutor("Max", "Favwell", "max.favwell@mail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Physics");
            subjects.add("Chemistry");
            subjects.add("Biology");
            subjects.add("Economics");
            max.addSubjects(subjects);
            max.setImagePathByName("sample_m1.jpeg");

            Tutor carrie = new Tutor("Carrie", "Walsh", "cwalsh@mail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Art");
            subjects.add("Photography");
            subjects.add("Geography");
            subjects.add("German");
            subjects.add("History");
            subjects.add("Japanese");
            carrie.addSubjects(subjects);
            carrie.setImagePathByName("sample_f2.jpeg");

            insertTutor(emily);
            insertTutor(max);
            insertTutor(carrie);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

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
    public List<Tutor> selectAllTutors() throws SQLException, IOException {
        String query = "SELECT * FROM tutors";
        PreparedStatement ps = con.prepareStatement(query);
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
