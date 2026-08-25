package com.shubham.student_management.mapper;

import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.entity.Courses;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Courses toEntity(CourseDto dto) {
        Courses course = new Courses();

        course.setCourseName(dto.courseName());
        course.setCourseCode(dto.courseCode());
        course.setDuration(dto.duration());
        course.setFee(dto.fee());
        course.setDescription(dto.description());
        course.setActive(dto.active());

        return course;
    }

    public CourseDto toDto(Courses course) {
        return new CourseDto(
                course.getId(),
                course.getCourseName(),
                course.getCourseCode(),
                course.getDuration(),
                course.getFee(),
                course.getDescription(),
                course.isActive()
        );
    }
}