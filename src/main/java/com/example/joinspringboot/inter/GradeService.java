package com.example.joinspringboot.inter;

import com.example.joinspringboot.model.Grade;

import java.util.List;

public interface GradeService {
    Grade createGrade(Grade grade);
    Grade getGradeById(Long id);
    List<Grade> getAllGrades();
    List<Grade> getGradesByStudentId(Long studentId);
    List<Grade> getGradesByStudentEmailId(String emailId);
    Grade updateGrade(Long id, Grade grade);
    void deleteGrade(Long id);
}
