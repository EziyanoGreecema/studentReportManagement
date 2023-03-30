package com.example.joinspringboot;

import jakarta.validation.constraints.Size;

public class DuplicateStudentException extends RuntimeException {
        public DuplicateStudentException(String message) {
            super(message);
        }
}
