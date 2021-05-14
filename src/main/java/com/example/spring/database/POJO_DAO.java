package com.example.spring.database;

import com.example.spring.model.Tutor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        Tutor howard = new Tutor("Howard", "Jackson", "hholt@mail.com", null, null);
        subjects = new ArrayList<>();
        subjects.add("Photography");
        subjects.add("Geology");
        subjects.add("German");
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
        subjects.add("German");
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
    public List<Tutor> selectAllTutors(int fromIndex, int numberOfTutors) {
        int toIndex = fromIndex + numberOfTutors;

        System.out.println("From: " + fromIndex);
        System.out.println("To: " + toIndex);
        System.out.println("Tutor List size: " + tutorList.size());

        if (fromIndex < tutorList.size() && toIndex < tutorList.size()) {   // fromIndex is indexed to 1, so we need to subtract 1 to 0 index it
            return tutorList.subList(fromIndex, toIndex);

        } else if (fromIndex < tutorList.size()) {
            return tutorList.subList(fromIndex, tutorList.size());

        } else {
            return null;
        }
    }

    @Override
    public int removeTutor(UUID uid) {
        for (Tutor t : tutorList) {
            if (t.getId().equals(uid)) {
                tutorList.remove(t);
                return 1;
            }
        }
        return -1;
    }
}
