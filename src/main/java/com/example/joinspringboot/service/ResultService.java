package com.example.joinspringboot.service;

import com.example.joinspringboot.*;
import com.example.joinspringboot.inter.GradeService;
import com.example.joinspringboot.model.Grade;
import com.example.joinspringboot.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService implements GradeService {
    private final ResultRepository resultRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository, SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.resultRepository = resultRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    public Subject getSubjectByName(String subjectName) {
        return subjectRepository.findByName(subjectName)
                .orElseThrow(() -> new IllegalArgumentException("Error due to: Subject not found with name " + subjectName));

    }

    public void saveResult(Long subjectId, int obtainedMarks,Long studentId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", subjectId));
      Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("student", "id", studentId));

       if(getGradeByStudentIdAndSubjectId(studentId, subjectId) )
                {
                    throw new ResourceNotFoundException("marks already given to student for this student and subject",subject.toString(),student);
                }
       else {
           resultRepository.save(Grade.builder().student(student).subject(subject).obtainedMarks(obtainedMarks).build());

       }
return;
  }

public List<Grade> getResult(Long studentId, Long subjectId) {
    List<Grade> grades = resultRepository.findByStudentId(studentId);
    Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", subjectId));
    return resultRepository.findByStudentId(studentId);
}

    public boolean getGradeByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        return resultRepository.existsByStudentIdAndSubjectId(studentId, subjectId);
    }

    @Override
    public Grade createGrade(Grade grade) {
        return resultRepository.save(grade);
    }

    @Override
    public Grade getGradeById(Long id) {
        Grade grade = resultRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Result"
                            , "id", id);
                }); return
                grade;
    }

    @Override
    public List<Grade> getAllGrades() {
        return resultRepository.findAll();
    }

    @Override
    public List<Grade> getGradesByStudentId(Long studentId) {
        return resultRepository.findByStudentId(studentId);
    }



    @Override
    public List<Grade> getGradesByStudentEmailId(String emailId) {
        return resultRepository.findByStudent_EmailId(emailId);

    }

    @Override
    public Grade updateGrade(Long id, Grade grade) {
        Grade existingGrade = getGradeById(id);
        existingGrade.setSubject(grade.getSubject());
        existingGrade.setObtainedMarks(grade.getObtainedMarks());
        existingGrade.setPublished(grade.isPublished());
        return resultRepository.save(existingGrade);
    }

    @Override
    public void deleteGrade(Long id) {
        Grade grade = getGradeById(id);
        resultRepository.delete(grade);
    }

  //  To calculate the percentage of students who passed and failed in a particular subject,

    public double getPassPercentage(String subjectName) {
        int passCount = 0;
        int totalCount = 0;
        var students=studentRepository.findAll();
        var subject=subjectRepository.findByName(subjectName).orElseThrow(()->new RuntimeException("no such subject exists"));
        for (Student student : students) {
//            GradeDetail gradeDetail = student.getGradeDetail(this);
            var gradeDetail=resultRepository.findBySubjectIdAndStudentId(student.getId(),subject.getId());
            if (gradeDetail != null) {
                if (subject.getPassMarks() != null) {
                    if (gradeDetail.getObtainedMarks() >= subject.getPassMarks()) {
                        passCount++;
                    }
                    totalCount++;
                }
            }
        }
        return (double) passCount / totalCount * 100;
    }


/*
    public double getFailPercentage(String subjectName) {
        int failCount = 0;
        int totalCount = 0;
        for (Student student : students) {
            GradeDetail gradeDetail = student.getGradeDetail(this);
            if (gradeDetail != null) {
                Subject subject = gradeDetail.getSubjectByName(subjectName);
                if (subject != null && subject.getPassMarks() != null) {
                    if (gradeDetail.getSubjectScore(subjectName) < subject.getPassMarks()) {
                        failCount++;
                    }
                    totalCount++;
                }
            }
        }
        return (double) failCount / totalCount * 100;
    }
}
*/


}
