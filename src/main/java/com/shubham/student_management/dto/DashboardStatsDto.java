package com.shubham.student_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {

    private Long totalStudents;
    private Long totalCourses;
    private String topPerformingCourse;
    private Long studentsEnrolledThisMonth;
}
