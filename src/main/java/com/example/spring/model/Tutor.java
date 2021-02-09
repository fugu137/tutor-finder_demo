package com.example.spring.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tutor {

    private final UUID id;
    private String firstName;
    private String surname;
    private final List<String> subjects;

    public Tutor(String firstName, String surname) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.surname = surname;
        this.subjects = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getSubjects() {
        return subjects;
    }
}
