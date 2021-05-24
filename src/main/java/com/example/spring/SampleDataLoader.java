package com.example.spring;

import com.example.spring.database.DAO;
import com.example.spring.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SampleDataLoader {

    private final DAO dao;

    @Autowired
    public SampleDataLoader(@Qualifier("sql_dao")DAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    public void initialize() {
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
            subjects.add("Geology");
            subjects.add("History");
            subjects.add("Japanese");
            carrie.addSubjects(subjects);
            carrie.setImagePathByName("sample_f2.jpeg");

            Tutor howard = new Tutor("Howard", "Jackson", "hholt@mail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Photography");
            subjects.add("Geology");
            subjects.add("Geography");
            subjects.add("History");
            howard.addSubjects(subjects);
            howard.setImagePathByName("sample_m2.jpeg");

            Tutor mary = new Tutor("Mary", "Abdul", "maryab@mail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Photography");
            subjects.add("History");
            subjects.add("Geography");
            mary.addSubjects(subjects);
            mary.setImagePathByName("sample_f3.webp");

            Tutor jeremy = new Tutor("Jeremy", "Jones", "j.jones@mail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Geography");
            subjects.add("Economics");
            subjects.add("History");
            subjects.add("Physics");
            subjects.add("Chemistry");
            subjects.add("Mathematics");
            jeremy.addSubjects(subjects);
            jeremy.setImagePathByName("sample_m3.jpeg");

            Tutor jane = new Tutor("Jane", "Yi", "janeyi@mail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Geography");
            subjects.add("Physics");
            subjects.add("Chemistry");
            subjects.add("Mathematics");
            subjects.add("Economics");
            jane.addSubjects(subjects);
            jane.setImagePathByName("sample_f4.jpeg");

            Tutor harry = new Tutor("Harry", "Bishop", "harryb@gmail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Geography");
            subjects.add("Physics");
            subjects.add("Geology");
            harry.addSubjects(subjects);
            harry.setImagePathByName("sample_m4.webp");

            Tutor jackson = new Tutor("Jackson", "Smith", "jsmithb@gmail.com", null, null);
            subjects = new ArrayList<>();
            subjects.add("Geography");
            subjects.add("Physics");
            subjects.add("Geology");
            jackson.addSubjects(subjects);
            jackson.setImagePathByName("sample_m4.jpeg");

            dao.insertTutor(emily);
            dao.insertTutor(max);
            dao.insertTutor(carrie);
            dao.insertTutor(howard);
            dao.insertTutor(mary);
            dao.insertTutor(jeremy);
            dao.insertTutor(jane);
            dao.insertTutor(harry);
            dao.insertTutor(jackson);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
