package com.example.spring.api;

import com.example.spring.model.Tutor;
import com.example.spring.service.TutorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@RequestMapping("api/tutor")
@CrossOrigin(origins = "http://mjduncan.net/webapp/tutor_service.html")
@RestController
public class Controller {

    private final TutorManager tutorManager;

    @Autowired
    public Controller(TutorManager tutorManager) {
        this.tutorManager = tutorManager;
    }

    @PostMapping(value = "api/tutor")
    public void addTutor(Tutor tutor) {
        tutorManager.addTutor(tutor);
    }

    @GetMapping(value = "api/tutor")
    public List<Tutor> getAllTutors() {
        return tutorManager.getAllTutors();
    }

//    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "api/tutor/{uidString}")
    public ResponseEntity<String> deleteTutor(@PathVariable String uidString) {
        UUID uid = UUID.fromString(uidString);

        if (tutorManager.removeTutor(uid) < 0) {
            return new ResponseEntity<>("Tutor id does not exist!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Tutor successfully deleted!", HttpStatus.OK);
    }
}
