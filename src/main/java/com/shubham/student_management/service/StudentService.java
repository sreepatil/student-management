package com.shubham.student_management.service;

import com.shubham.student_management.dto.StudentDto;
import org.springframework.data.domain.Page;

public interface StudentService {

    StudentDto createStudent(StudentDto studentDto);

    Page<StudentDto> getStudent(int page, int size);

    boolean existsByEmail(String email);

    StudentDto getStudentId(Long id);

    StudentDto updateStudent(Long id,StudentDto studentDto);

    boolean existsByEmailAndIdNot( String email, Long id);

}
