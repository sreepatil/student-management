package com.shubham.student_management.service.impl;

import com.shubham.student_management.dto.DashboardStatsDto;
import com.shubham.student_management.repository.CourseRepository;
import com.shubham.student_management.repository.EnrollmentRepository;
import com.shubham.student_management.repository.StudentRepository;
import com.shubham.student_management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public DashboardStatsDto getDashboardStats() {
        Long totalStudents = studentRepository.count();
        Long totalCourses = courseRepository.count();

        String topPerformingCourse = getTopPerformingCourse();

        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(LocalTime.MAX);


        long studentEnrolledThisMonth = enrollmentRepository.countDistinctStudentByEnrollDateBetween(startDate, endDate);

        DashboardStatsDto dashboardStatsDto = new DashboardStatsDto();
        dashboardStatsDto.setTotalStudents(totalStudents);
        dashboardStatsDto.setTotalCourses(totalCourses);
        dashboardStatsDto.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDto.setStudentsEnrolledThisMonth(
                studentEnrolledThisMonth
        );
        return dashboardStatsDto;
    }

    private String getTopPerformingCourse(){
        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getCourse().getCourseName(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
