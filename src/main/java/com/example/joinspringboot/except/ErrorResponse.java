package com.example.joinspringboot.except;

import org.springframework.http.HttpStatusCode;

import java.util.List;

public class ErrorResponse {

    private int status;
    private List<String> errors;

    public ErrorResponse(int status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
