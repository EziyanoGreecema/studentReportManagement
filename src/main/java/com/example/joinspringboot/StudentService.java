package com.example.joinspringboot;


import com.example.joinspringboot.except.CustomException;
import com.example.joinspringboot.except.DuplicateStudentResponseEntityException;
import com.example.joinspringboot.except.ResourceNotFoundException2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch all students");
        }
    }

    public Student getStudentById(Long studentId) {
        try {
            return studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException2("Student not found","studentID",studentId));
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch student");
        }
    }


    public Student createStudent(Student student) throws DuplicateStudentResponseEntityException {
        if(studentRepository.findStudentsByEmailId(student.getEmailId()).isPresent())
            throw new DuplicateStudentResponseEntityException("Student with the given email and name already exists!",student.getEmailId(),student.getFirstName());
        else return studentRepository.save(student);
        //        catch (Exception e)
//        {
//            if (e instanceof NonUniqueResultException )
//                throw new NonUniqueResultException("found many with such iD");
//            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create student");
//        }

    }

    public Student updateStudent(Long studentId, Student student) {
        try {
            Student existingStudent = studentRepository.findById(studentId)
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Student not found"));
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmailId(student.getEmailId());
            return studentRepository.save(existingStudent);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update student");
        }
    }
    public void deleteStudent(Long studentId) {
        try {
            studentRepository.deleteById(studentId);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete student");
        }
    }
}
