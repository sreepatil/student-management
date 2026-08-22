package com.shubham.student_management.service;

import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.entity.Courses;
import org.springframework.data.domain.Page;

public interface CourseService {

    CourseDto createCourse(CourseDto courseDto);

    Page<Courses> getCourses(int page);

    boolean existsByCourseCode(String code);
}
