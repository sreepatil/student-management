package com.shubham.student_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentSummaryDto {
        private Long studentId;
        private String studentName;
        private String email;
        private int courseCount;
        private BigDecimal totalFee;
        private List<CourseDto> courseList = new ArrayList<>();

}
