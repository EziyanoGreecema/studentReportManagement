package com.example.joinspringboot;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 5, max = 10, message = "Student's name must be between 5 and 10 characters")
    private String firstName;
    @NotBlank()
    private String lastName;
    private String emailId;
    private String level;
    private String section;
/*
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "joined_column_student_id_subject_id")
    private List<Subject> subject;
*//*

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Student() {

    }

    public Student(String firstName, String lastName, String emailId) {
         this.firstName = firstName;
         this.lastName = lastName;
         this.emailId = emailId;
    }
 

 
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    @Column(name = "email_address", nullable = false)
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }*/

//    @Override
//    public String toString() {
//        return "Student [section=\", + section + + level=\" + level + id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
//       + "]";
//    }




}