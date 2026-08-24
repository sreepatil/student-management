package com.shubham.student_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CourseDto(
        Long id,
        @NotBlank(message = "Course name is required")
        @Size(min = 3, max = 50, message = "name should be between 3 to 50 characters")
        String courseName,

        @NotBlank(message = "Course code can not be null")
        String courseCode,

        @NotBlank(message = "Duration is required")
        String duration,

        @NotNull(message = "fees is required")
        BigDecimal fee,

        @Size(min = 3, max = 500, message = "description should be between 3 to 500 characters")
        String description,

        boolean active
) {
}
