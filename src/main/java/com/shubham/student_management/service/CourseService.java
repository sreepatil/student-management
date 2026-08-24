package com.shubham.student_management.service;

import com.shubham.student_management.dto.CourseDto;
import org.springframework.data.domain.Page;

public interface CourseService {

    CourseDto createCourse(CourseDto courseDto);

    Page<CourseDto> getCourses(int page, int size);

    boolean existsByCourseCode(String code);

    CourseDto getCourseId(Long id);

    CourseDto updateCourse(Long id, CourseDto courseDto);

    boolean existsByCourseCodeAndIdNot(String code, Long id);
}
