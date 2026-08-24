package com.shubham.student_management.service.impl;

import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.entity.Courses;
import com.shubham.student_management.repository.CourseRepository;
import com.shubham.student_management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseDto createCourse(CourseDto courseDto) {

        Courses courses = new Courses();

        courses.setCourseName(courseDto.courseName());
        courses.setCourseCode(courseDto.courseCode());
        courses.setDuration(courseDto.duration());
        courses.setFee(courseDto.fee());
        courses.setDescription(courseDto.description());

        courseRepository.save(courses);

        return new CourseDto(
                courses.getId(),
                courses.getCourseName(),
                courses.getCourseCode(),
                courses.getDuration(),
                courses.getFee(),
                courses.getDescription(),
                courses.isActive()
        );
    }

    @Override
    public Page<CourseDto> getCourses(int page, int size) {

        PageRequest pageRequest =
                PageRequest.of(page, size, Sort.Direction.DESC, "id");

        return courseRepository.findByActiveTrue(pageRequest)
                .map(course -> new CourseDto(
                        course.getId(),
                        course.getCourseName(),
                        course.getCourseCode(),
                        course.getDuration(),
                        course.getFee(),
                        course.getDescription(),
                        course.isActive()
                ));
    }

    @Override
    public boolean existsByCourseCode(String code) {
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseId(Long id) {
        Courses course = courseRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Course Not Found"));
        return new  CourseDto(
                        course.getId(),
                        course.getCourseName(),
                        course.getCourseCode(),
                        course.getDuration(),
                        course.getFee(),
                        course.getDescription(),
                        course.isActive());
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {

        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course Not Found"));

        course.setCourseName(courseDto.courseName());
        course.setCourseCode(courseDto.courseCode());
        course.setDuration(courseDto.duration());
        course.setFee(courseDto.fee());
        course.setDescription(courseDto.description());
        course.setActive(courseDto.active());

        Courses updated = courseRepository.save(course);

        return new CourseDto(
                updated.getId(),
                updated.getCourseName(),
                updated.getCourseCode(),
                updated.getDuration(),
                updated.getFee(),
                updated.getDescription(),
                updated.isActive()
        );
    }

    @Override
    public boolean existsByCourseCodeAndIdNot(String code, Long id) {
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code, id);
    }
}
