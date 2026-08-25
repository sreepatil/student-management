package com.shubham.student_management.mapper;

import com.shubham.student_management.dto.StudentDto;
import com.shubham.student_management.entity.Students;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Students toEntity(StudentDto dto) {
        Students student = new Students();

        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
        student.setPhoneNumber(dto.phoneNumber());
        student.setAddress(dto.address());

        return student;
    }

    public StudentDto toDto(Students student) {
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getAddress(),
                student.isActive()
        );
    }
}
