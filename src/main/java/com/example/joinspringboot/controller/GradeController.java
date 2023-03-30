package com.example.joinspringboot.controller;

import com.example.joinspringboot.Student;
import com.example.joinspringboot.SubjectRepository;
import com.example.joinspringboot.inter.GradeService;
import com.example.joinspringboot.model.Grade;
import com.example.joinspringboot.model.GradeRequest;
import com.example.joinspringboot.model.Subject;
import com.example.joinspringboot.security.services.UserDetailsImpl;
import com.example.joinspringboot.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/grade")
@PreAuthorize("hasRole('ADMIN')")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ResultService resultService;

    @GetMapping("student/{emailId}")
    public ResponseEntity<List<Grade>> getGradesByStudentId(Authentication authenciation , @PathVariable String emailId) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authenciation.getPrincipal();
        System.out.println("userPrincipalorfromownemail"+userPrincipal.getEmail());
        System.out.println("userPrincipalorfromownemail"+userPrincipal.getEmail());

        if (userPrincipal == null) {
            var a= Collections.singletonMap("isLoggedIn", false);
        }
        var b=    Collections.singletonMap("isLoggedIn", true);
        System.out.println(userPrincipal.getPassword());


        List<Grade> grades = gradeService.getGradesByStudentEmailId(emailId);

        return new ResponseEntity<>(grades,HttpStatus.ACCEPTED);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/student/{gradeId}/publish/{status}")
    public ResponseEntity<String> setPublish(@PathVariable Long gradeId,@PathVariable boolean status) {
        var grade=gradeService.getGradeById(gradeId);
        grade.setPublished(status);
  gradeService.updateGrade(gradeId,grade);
        return new ResponseEntity<>( "done",HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Grade> addGradeToStudent(@RequestBody GradeRequest request) {
        resultService.saveResult(request.getSubjectId(),request.getMarksObtained(),request.getStudentId());
            return new ResponseEntity<>(gradeService.getGradeById(request.getStudentId()), HttpStatus.CREATED);
        }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long id) {
        System.out.println(id);
        Grade grade = gradeService.getGradeById(id);
        System.out.println(grade);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

  /*  @PostMapping("/student/setPassMarks/{passMark}")
    public ResponseEntity<String> setPassMarksForListStudent(@RequestBody List<Student> students,@PathVariable Integer passMark) {
        var gradeBuilder=Grade.builder();

        students.forEach(gradeBuilder::student);
        gradeService.createGrade(Grade.builder().build());
         gradeService.getGradeById(gradeId).setPublished(status);
        return new ResponseEntity<>( "done",HttpStatus.OK);
    }*/
    @PostMapping("/set-pass-mark/{subject}")
    public void setPassMarkForSubject(@PathVariable("subject") String subjectName,
                                      @RequestBody List<Student> students,
                                      @RequestParam("passMark") Integer passMark) {
        Subject subject = resultService.getSubjectByName(subjectName);
            if (subject == null) {
            throw new IllegalArgumentException("Invalid subject name: " + subjectName);
        }

        if (passMark == null || passMark < 0 || passMark < 30) {
            throw new IllegalArgumentException("Invalid pass mark: " + passMark);
        }
        subject.setPassMarks(passMark);


        subject.setPassMarks(passMark);
        students.forEach(student -> {
            Grade grade = Grade.builder()
                    .student(student)
                    .subject(subject)
                    .build();
            gradeService.createGrade(grade);
        });
    }


    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        Grade updatedGrade = gradeService.updateGrade(id, grade);
        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok().build();
    }
}