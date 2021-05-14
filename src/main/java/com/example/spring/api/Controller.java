package com.example.spring.api;

import com.example.spring.model.Tutor;
import com.example.spring.service.TutorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping(path="/api")
@RestController
public class Controller {

    private final TutorManager tutorManager;

    @Autowired
    public Controller(TutorManager tutorManager) {
        this.tutorManager = tutorManager;
    }

    @PostMapping(value = "/tutor")
    public void addTutor(Tutor tutor) {
        try {
            tutorManager.addTutor(tutor);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/tutor")
    public List<Tutor> getAllTutors(int fromIndex, int numberOfTutors) {
        try {
            return tutorManager.getAllTutors(fromIndex, numberOfTutors);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @DeleteMapping(value = "/tutor/{uidString}")
    public ResponseEntity<String> deleteTutor(@PathVariable String uidString) {
        UUID uid = UUID.fromString(uidString);
        int response;

        try {
            response = tutorManager.removeTutor(uid);

            if (response < 0) {
                return new ResponseEntity<>("Tutor id does not exist!", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Tutor successfully deleted!", HttpStatus.OK);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
