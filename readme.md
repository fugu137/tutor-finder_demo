# Tutor Finder Application #

Tutor Finder is a web application built using Spring Boot, JavaScript, CSS, and MySQL. The application allows users to store and load information about tutors via their web browser.

Note: I built this application to practice using Spring Boot, JavaScript, CSS, and SQL (and MySQL). It is a demo and a work in progress, which means that there are some design choices which would be odd in a fully functional web app. For instance, for now there is no separate UI for students and admins. A user can both browse profiles (as a student looking for a tutor would), and add or remove tutor profiles (as an admin would). I have also avioded using some of Spring's handy features like Spring Data JPA, in order to do more of the work manually (like writing SQL queries).

#### Browse Tutors ####
![demo1.png](demo1.PNG)

#### Add Tutors ####
![demo2.png](demo2.PNG)

#### Browse on Mobile ####
![demo3.png](demo3.png)

## Running Tutor Finder ##
To run the application:
1. Clone the repository.
2. Install MySQL if you don't have it. (ALternatively, you can try the application using the POJO_DAO class by changing `@Qualifier("sql_dao")` to `@Qualifier("pojo_dao")` in the TutorManager and SampleDataLoader classes. The filter buttons do not current work while using the POJO_DAO class.)
3. Execute the following commands in MySQL Workbench or MySQL Command Line Client:
  ```
  CREATE DATABASE tutor_finder;
  USE tutor_finder;

  CREATE TABLE tutors(
    uuid VARCHAR(100) PRIMARY KEY,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(100),
    imageURL VARCHAR(100)
  );
  
  CREATE TABLE tutors_subjects(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    tutorid VARCHAR(100),
    subject VARCHAR(100),
    FOREIGN KEY (tutorid) 
      REFERENCES tutors(uuid)
  );
  ```
6. Set the username and password variables in the DatabaseConnection class to your MySQL username and password.
7. Run `Application.java` at `\src\main\java\com\example\spring\` to start the server.
8. Open `index.html` in a web browser to use the application. You should see some sample tutor profiles. Tutors can be added by clicking the icon at the top right of the screen.\*
