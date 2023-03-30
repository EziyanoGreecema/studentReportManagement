package com.example.joinspringboot.controller;

import com.example.joinspringboot.DuplicateStudentException;
import com.example.joinspringboot.ResourceNotFoundException;
import com.example.joinspringboot.Student;
import com.example.joinspringboot.StudentService;
import com.example.joinspringboot.except.CustomException;
import com.example.joinspringboot.except.DuplicateStudentResponseEntityException;
import com.example.joinspringboot.except.ErrorResponse;
import com.example.joinspringboot.except.ResourceNotFoundException2;
import jakarta.persistence.NonUniqueResultException;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/student")
//    hasRole('USER') or hasRole('MODERATOR') or
@PreAuthorize(" hasRole('ADMIN')")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) throws ResourceNotFoundException {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok().body(student);
    }


    @PostMapping()
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student) throws DuplicateStudentResponseEntityException {
        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));

        }catch (DuplicateStudentException e)
        { return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails) throws ResourceNotFoundException {
        Student updatedStudent = studentService.updateStudent(id, studentDetails);
        return ResponseEntity.ok().body(updatedStudent);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) throws ResourceNotFoundException {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
        @GetMapping("/all")
        @PreAuthorize(" hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Student>> getStudentById() throws ResourceNotFoundException {
        List<Student> student = studentService.getAllStudents();
        return ResponseEntity.ok().body(student);
    }



   @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateStudentException(NonUniqueResultException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), Collections.singletonList(ex.getCause()+ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException2.class)
    public ResponseEntity<ErrorResponse> handleDuplicateStudentException(ResourceNotFoundException2 ex) {
        List<String> errors= new ArrayList<>();
        errors.add("something went wrong"+ex.getFieldName()+ex.getMessage());
        errors.add(ex.getFieldName());
        errors.add((String) ex.getFieldValue());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateStudentException(CustomException ex) {
        List<String> errors= new ArrayList<>();
        errors.add("something went wrong"+ex.getMessage());
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
