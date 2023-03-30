package com.example.joinspringboot.service;

import com.example.joinspringboot.ResourceNotFoundException;
import com.example.joinspringboot.SubjectRepository;
import com.example.joinspringboot.model.Subject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject saveSubject(Subject subject) {
        if (subjectRepository.getSubjectByCode(subject.getCode()) != null) {
            throw new ResourceNotFoundException("Subject with code ", subject.getCode(), " already exists");
        }
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjects() {
       return subjectRepository.findAll();
    }
    public Optional<Subject> getSubjects(String subjectCode) {
       return Optional.ofNullable(subjectRepository.getSubjectByCode(subjectCode));
    }
    public Optional<Subject> getSubjectById(Long subjectId) {
       return (subjectRepository.findById(subjectId));
    }
    public void updateSubject(Subject subject) {
        subjectRepository.delete(subject);
    }

    @PutMapping("/notes/{id}")
    public Subject updateNote(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody Subject noteDetails) {

        Subject subject = subjectRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("no such subject exists", "id", noteId));
        subject.setPassMarks(noteDetails.getPassMarks());
        subject.setFullMarks(noteDetails.getFullMarks());
        subject.setName(noteDetails.getName());
        subject.setCode(noteDetails.getCode());
        return subjectRepository.save(subject);
    }
    public void deleteSubject(Subject subject) {
        subjectRepository.delete(subject);
    }

    /*   public void saveSubject(String subCode, String subName, int fullMarks, int passMarks) {
        Subject subject = new Subject();
        subject.setCode(subCode);
        subject.setName(subName);
        subject.setFullMarks(fullMarks);
        subject.setPassMarks(passMarks);

        subjectRepository.save(subject);
    }
*/


}