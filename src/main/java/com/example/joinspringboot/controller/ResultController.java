package com.example.joinspringboot.controller;

import com.example.joinspringboot.StudentService;
import com.example.joinspringboot.inter.GradeService;
import com.example.joinspringboot.model.Grade;
import com.example.joinspringboot.security.services.UserDetailsImpl;
import com.example.joinspringboot.service.ResultService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/result")
public class ResultController {
  @Autowired
  private GradeService gradeService;
  @Autowired
  private ResultService resultService;
  @Autowired
  private StudentService studentService;

  @GetMapping("/user/{email}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<Grade>> getGradesByStudentId(Authentication authenciation , @PathVariable String email) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authenciation.getPrincipal();

    if (!userPrincipal.getEmail().equals(email) ) {
      var a= Collections.singletonMap("isLoggedIn", false);
      return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
    var b=    Collections.singletonMap("isLoggedIn", true);
    System.out.println(userPrincipal.getPassword());


      List<Grade> grades = gradeService.getGradesByStudentEmailId(userPrincipal.getEmail());
      return new ResponseEntity<>( grades.stream().filter(Grade::isPublished).collect(Collectors.toList()),HttpStatus.OK);

  }



  @GetMapping("allstudents")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<Grade>> getAllGrades() {
    List<Grade> grades = gradeService.getAllGrades();
    return new ResponseEntity<>(grades, HttpStatus.OK);
  }
}
