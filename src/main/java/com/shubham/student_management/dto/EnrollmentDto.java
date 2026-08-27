package com.shubham.student_management.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {

    @NotNull(message = "Student is required")
    private Long studentId;

    @NotEmpty(message = "Select atLeast one course")
    private List<Long> courseIds = new ArrayList<>();
}
