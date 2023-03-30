package com.example.joinspringboot;

import com.example.joinspringboot.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Grade, Long>{

        List<Grade> findByStudentId(Long studentId);
        List<Grade> findByStudent_EmailId(String  emailId);
        Grade findBySubjectIdAndStudentId(Long studentId,Long subjectId);


        boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);

}