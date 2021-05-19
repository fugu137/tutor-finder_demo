package com.example.spring.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tutor {

    private UUID id;
    private String firstName;
    private String surname;
    private String email;
    private final List<String> subjects;
    private String imagePath;

    public Tutor(String firstName, String surname, String email, List<String> subjects, MultipartFile picture) throws IOException {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.subjects = new ArrayList<>();
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
        addImage(picture);
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID();
    }
    public void setId(UUID id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void addSubjects(List<String> subjects) {
        this.subjects.addAll(subjects);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public void addImage(MultipartFile picture) throws IOException {
        if (picture == null) {
            this.imagePath = null;
            return;
        }

        this.imagePath = createImageAndReturnPath(picture);
    }

    private String createImageAndReturnPath(MultipartFile picture) throws IOException {
        String fileName = picture.getOriginalFilename();
        assert fileName != null;
        String[] parts = fileName.split("\\.");
        String extension = "." + parts[parts.length - 1];

        String directory = "src/main/resources/static/profile_images/";
        String relativePath = directory + this.email + extension;
        Path path = Paths.get(relativePath);

        byte[] bytes = picture.getBytes();

        try {
            Files.write(path, bytes);
            return relativePath;

        } catch (Exception e) {
            throw new IOException("Unable to create file." + e.getMessage());
        }
    }

    public void setImagePathByName(String pictureName) {                   // Method for loading sample tutor data (tutor images stored in directory below).
        String directory = "src/main/resources/static/profile_images/";
        this.imagePath = directory + pictureName;
    }
}
