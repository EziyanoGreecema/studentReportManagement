package com.example.joinspringboot.except;

import jakarta.persistence.NonUniqueResultException;
import lombok.Data;

@Data
public class ResourceNotFoundException2 extends NonUniqueResultException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException2(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}