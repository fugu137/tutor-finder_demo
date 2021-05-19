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

    private static final Connection con = DatabaseConnection.getConnection();

    @Override
    public int insertTutor(Tutor tutor) {
        if (tutor == null) {
            return -1;
        }
        tutor.setId();

        int status = 0;

        String query = "INSERT INTO tutors(uuid, firstname, lastname, email, imageURL) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertTutorPs = con.prepareStatement(query)) {

            con.setAutoCommit(false);

            insertTutorPs.setString(1, tutor.getId().toString());
            insertTutorPs.setString(2, tutor.getFirstName());
            insertTutorPs.setString(3, tutor.getSurname());
            insertTutorPs.setString(4, tutor.getEmail());
            insertTutorPs.setString(5, tutor.getImagePath());

            List<String> subjects = tutor.getSubjects();

            System.out.println(insertTutorPs);
            status = insertTutorPs.executeUpdate();

            String query2 = "INSERT INTO tutors_subjects(tutorid, subject) VALUES (?, ?)";
            try (PreparedStatement ps2 = con.prepareStatement(query2)) {

                for (String subject : subjects) {
                    ps2.setString(1, tutor.getId().toString());
                    ps2.setString(2, subject);
                    System.out.println(ps2);
                    ps2.executeUpdate();
                }

                con.commit();
                return status;

            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Tutor> selectAllTutors(int fromIndex, int numberOfTutors, String[] filters) {
        int filterCount = filters.length;

        String query;
        ResultSet rs;

        if (filterCount > 0) {
            StringBuilder querySB = new StringBuilder();
            querySB.append("SELECT uuid, firstname, lastname, email,imageURL FROM tutors_subjects " +
                    "JOIN tutors ON tutors.uuid = tutors_subjects.tutorid WHERE subject IN(");

            String list = "?, ".repeat(filterCount);
            list = list.replaceAll(", $", "");
            querySB.append(list);

            querySB.append(") GROUP BY tutors_subjects.tutorid HAVING COUNT(DISTINCT subject) = ? LIMIT ? OFFSET ?;");
            query = querySB.toString();

            try (PreparedStatement filterPs = con.prepareStatement(query)) {

                for (int i = 0; i < filterCount; i++) {
                    filterPs.setString(i + 1, filters[i]);
                }

                filterPs.setInt(filterCount + 1, filterCount);
                filterPs.setInt(filterCount + 2, numberOfTutors);
                filterPs.setInt(filterCount + 3, fromIndex);

                rs = filterPs.executeQuery();
                return getSubjectsAndBuildTutorList(rs);

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            query = "SELECT * FROM tutors LIMIT ? OFFSET ?;";

            try(PreparedStatement noFilterPs = con.prepareStatement(query)) {
                noFilterPs.setInt(1, numberOfTutors);
                noFilterPs.setInt(2, fromIndex);

                rs = noFilterPs.executeQuery();
                return getSubjectsAndBuildTutorList(rs);

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private List<Tutor> getSubjectsAndBuildTutorList(ResultSet rs) throws SQLException {
        List<Tutor> tutors = new ArrayList<>();

        while (rs.next()) {
            List<String> subjects = new ArrayList<>();

            String subjectQuery = "SELECT * FROM tutors_subjects WHERE tutorid = ? GROUP BY subject";
            String idString = rs.getString("uuid");

            try (PreparedStatement ps2 = con.prepareStatement(subjectQuery)) {
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

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

        return tutors;
    }

    @Override
    public int removeTutor(UUID uid) {
        String query = "DELETE FROM tutors WHERE uuid = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, uid.toString());
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
