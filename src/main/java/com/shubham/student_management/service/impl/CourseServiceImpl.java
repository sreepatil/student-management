package com.shubham.student_management.service.impl;

import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.entity.Courses;
import com.shubham.student_management.mapper.CourseMapper;
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
    private final CourseMapper courseMapper;

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Courses course = courseMapper.toEntity(courseDto);
        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public Page<CourseDto> getCourses(int page, int size) {

        PageRequest pageRequest =
                PageRequest.of(page, size, Sort.Direction.DESC, "id");

        return courseRepository.findByActiveTrue(pageRequest)
                .map(courseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseId(Long id) {

        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course Not Found"));

        return courseMapper.toDto(course);
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

        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public boolean existsByCourseCode(String code) {
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public boolean existsByCourseCodeAndIdNot(String code, Long id) {
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code, id);
    }
}