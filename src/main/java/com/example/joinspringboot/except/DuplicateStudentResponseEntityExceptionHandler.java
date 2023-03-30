package com.example.joinspringboot.except;

import com.example.joinspringboot.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DuplicateStudentResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
      = {  DuplicateStudentResponseEntityException.class })
    protected ResponseEntity<Object> handleConflict(
            DuplicateStudentResponseEntityException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific"+ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
          new HttpHeaders(), HttpStatus.CONFLICT, request);

    }
}