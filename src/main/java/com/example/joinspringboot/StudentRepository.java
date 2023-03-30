package com.example.joinspringboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    Optional<Student> findStudentsByEmailIdAndFirstName(String emailId, String firstName);
    Optional<Student> findStudentsByEmailId(String emailId);

}
/*
      if (studentRepository.findByFirstNameAndLastNameAndDateOfBirth(student.getFirstName(), student.getLastName(), student.getDateOfBirth()).isPresent()) {
              throw new DuplicateStudentException("Student with same details already exists");
              }
*/
