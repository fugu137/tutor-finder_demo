package com.example.spring.database;

import com.example.spring.model.Tutor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public List<Tutor> selectAllTutors(int fromIndex, int numberOfTutors, String[] filters) {
        int toIndex = fromIndex + numberOfTutors;

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
