package com.example.joinspringboot.controller;

import com.example.joinspringboot.ResourceNotFoundException;
import com.example.joinspringboot.model.Subject;
import com.example.joinspringboot.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;


    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getSubjects();
    }
/*
    @GetMapping("/report/{id}")
    public List<StudentReportCard> getAllEmployees(@PathVariable(value = "id") Long id) {
        return service.getReportCardsByStudentId(id);
    }
    @PostMapping("add/report/")
    public List<StudentReportCard> addReport(@PathVariable(value = "id") Long id) {
        return service.getReportCardsByStudentId(id);
    }

*/

    @GetMapping("/subject/{code}")
    public ResponseEntity<Subject> getEmployeeById(@PathVariable(value = "id") String subjectCode)
        throws ResourceNotFoundException {
        Subject subject = subjectService.getSubjects(subjectCode)
          .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: ","subjectCode", subjectCode));
        return ResponseEntity.ok().body(subject);
    }
    @GetMapping("/subject/{id}")
    public ResponseEntity<Subject> getEmployeeById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        Subject subject = subjectService.getSubjectById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: ","subjectCode", id));
        return ResponseEntity.ok().body(subject);
    }
    
    @PostMapping()
    public Subject createSubject(@Valid @RequestBody Subject subject) {
        return subjectService.saveSubject(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateEmployee(@PathVariable(value = "id") Long subjectId,
         @Valid @RequestBody Subject subject) throws ResourceNotFoundException {
        Subject newSubject = subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: ","subjectCode", subjectId));
        newSubject.setName(subject.getName());
        newSubject.setCode(subject.getCode());
        newSubject.setFullMarks(subject.getFullMarks());
        newSubject.setPassMarks(subject.getPassMarks());
        final Subject updatedSubject = subjectService.saveSubject(newSubject);
        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long subjectId)
         throws ResourceNotFoundException {
        Subject newSubject = subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: ","subjectCode", subjectId));
      try{
          subjectService.deleteSubject(newSubject);
          Map<String, Boolean> response = new HashMap<>();
          response.put("deleted", Boolean.TRUE);
          return response;

      }catch (Exception e){
          Map<String, Boolean> response = new HashMap<>();
          response.put("deleted because of"+e.getCause()+e.getMessage(), Boolean.FALSE);
          return response;
      }
    }
}