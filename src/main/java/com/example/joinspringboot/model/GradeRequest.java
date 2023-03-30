package com.example.joinspringboot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GradeRequest implements Serializable {
    private Long subjectId;
    private Long studentId;
    private int marksObtained;

}
