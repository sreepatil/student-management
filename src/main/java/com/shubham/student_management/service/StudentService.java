package com.shubham.student_management.service;

import com.shubham.student_management.dto.StudentDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    StudentDto createStudent(StudentDto studentDto);

    boolean existsByEmail(String email);

    StudentDto getStudentId(Long id);

    StudentDto updateStudent(Long id,StudentDto studentDto);

    boolean existsByEmailAndIdNot( String email, Long id);

    Page<StudentDto> getStudents(int page, int size, boolean active);

    List<StudentDto> getAllStudents();
}
