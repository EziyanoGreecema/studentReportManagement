package com.example.joinspringboot.except;

import org.springframework.stereotype.Component;

public class DuplicateStudentResponseEntityException extends Exception {
    private String kna;
    private String resourceName;
    private Object fieldValue;

    public DuplicateStudentResponseEntityException(String resourceName, String kna, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, kna, fieldValue));
        this.resourceName = resourceName;
        this.kna = kna;
        this.fieldValue = fieldValue;
    }
}
