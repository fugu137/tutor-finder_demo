# Tutor Finder Application #

*Tutor Finder is a web application built using Spring Boot, Javascript, CSS, and MySQL. The application allows users to store and load information about tutors via their web browser.*

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
    uuid VARCHAR(100),
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(100),
    imageURL VARCHAR(100),
    PRIMARY KEY (uuid)
  );
  
  CREATE TABLE tutors_subjects(
    id INTEGER NOT NULL AUTO_INCREMENT,
    tutorid VARCHAR(100),
    subject VARCHAR(100),
    PRIMARY KEY (id)
  );
  ```
6. Set the username and password variables in the DatabaseConnection class to your MySQL username and password.
7. Run `Application.java` at `\src\main\java\com\example\spring\` to start the server.
8. Open `index.html` in a web browser to use the application. You should see some sample tutor profiles. Tutors can be added by clicking the icon at the top right of the screen.\*

*Note: this is a work in progress and some features have not been implemented. (It's also just for demo purposes, which is why the user can both search and add tutors as if they are an admin.)
