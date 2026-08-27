package com.shubham.student_management.service;

import com.shubham.student_management.dto.EnrollmentDto;
import com.shubham.student_management.dto.EnrollmentSummaryDto;
import org.springframework.data.domain.Page;

public interface EnrollmentService {

    void enrollmentStudentToCourses(EnrollmentDto enrollmentDto);

    Page<EnrollmentSummaryDto> getEnrolledStudents(int page, int size, boolean active);

    EnrollmentSummaryDto findEnrolledStudentCourseDetails(Long studentId);

}
