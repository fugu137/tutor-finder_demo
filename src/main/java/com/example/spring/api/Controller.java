package com.example.spring.api;

import com.example.spring.model.Tutor;
import com.example.spring.service.TutorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/tutor")
@CrossOrigin(origins = "http://mjduncan.net/webapp/tutor_service.html")
@RestController
public class Controller {

    private final TutorManager tutorManager;

    @Autowired
    public Controller(TutorManager tutorManager) {
        this.tutorManager = tutorManager;
    }

    @PostMapping
    public void addTutor(Tutor tutor) {
        tutorManager.addTutor(tutor);
    }

    @GetMapping
    public List<Tutor> getAllTutors() {
        return tutorManager.getAllTutors();
    }
}
