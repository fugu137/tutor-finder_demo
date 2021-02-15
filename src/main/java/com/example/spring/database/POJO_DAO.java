package com.example.spring.database;

import com.example.spring.model.Tutor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("pojo_dao")
public class POJO_DAO implements DAO {

    private static List<Tutor> tutorList = new ArrayList<>();

    public POJO_DAO() throws IOException {
        addSampleData();
    }

    //TODO: remove temporary method
    private void addSampleData() throws IOException {
        Tutor emily = new Tutor("Emily", "Falah", "emily.falah@mail.com", null, null);
        List<String> subjects = new ArrayList<>();
        subjects.add("Art");
        subjects.add("Photography");
        subjects.add("English");
        subjects.add("French");
        subjects.add("Arabic");
        emily.addSubjects(subjects);
        emily.setImagePath("sample_f1.jpeg");

        Tutor max = new Tutor("Max", "Favwell", "max.favwell@mail.com", null, null);
        subjects = new ArrayList<>();
        subjects.add("Physics");
        subjects.add("Chemistry");
        subjects.add("Biology");
        subjects.add("Economics");
        max.addSubjects(subjects);
        max.setImagePath("sample_m1.jpeg");

        Tutor carrie = new Tutor("Carrie", "Walsh", "cwalsh@mail.com", null, null);
        subjects = new ArrayList<>();
        subjects.add("Art");
        subjects.add("Photography");
        subjects.add("Geography");
        subjects.add("German");
        subjects.add("History");
        subjects.add("Japanese");
        carrie.addSubjects(subjects);
        carrie.setImagePath("sample_f2.jpeg");

        Tutor howard = new Tutor("Howard", "Jackson", "hholt@mail.com", null, null);
        subjects = new ArrayList<>();
        subjects.add("Photography");
        subjects.add("Geology");
        subjects.add("German");
        subjects.add("History");
        howard.addSubjects(subjects);
        howard.setImagePath("sample_m2.jpeg");

        Tutor mary = new Tutor("Mary", "", "", null, null);
        mary.setImagePath("sample_f3.webp");

        Tutor jeremy = new Tutor("Jeremy", "", "", null, null);
        jeremy.setImagePath("sample_m3.jpeg");

        Tutor jane = new Tutor("Jane", "", "", null, null);
        jane.setImagePath("sample_f4.jpeg");

        Tutor harry = new Tutor("Harry", "", "", null, null);
        harry.setImagePath("sample_m4.webp");

        tutorList.add(emily);
        tutorList.add(max);
        tutorList.add(carrie);
        tutorList.add(howard);
        tutorList.add(mary);
        tutorList.add(jeremy);
        tutorList.add(jane);
        tutorList.add(harry);
    }

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
