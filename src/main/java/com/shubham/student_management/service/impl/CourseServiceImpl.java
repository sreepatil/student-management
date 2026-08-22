package com.shubham.student_management.service.impl;

import com.shubham.student_management.config.ModelMapperConfig;
import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.entity.Courses;
import com.shubham.student_management.repository.CourseRepository;
import com.shubham.student_management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Courses courses = modelMapper.map(courseDto, Courses.class);
        courseRepository.save(courses);
        return modelMapper.map(courses, CourseDto.class);
    }

    @Override
    public Page<Courses> getCourses(int page) {
        return courseRepository.findAll(
                PageRequest.of(page, 10)
        );
    }

    @Override
    public boolean existsByCourseCode(String code) {
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }
}
