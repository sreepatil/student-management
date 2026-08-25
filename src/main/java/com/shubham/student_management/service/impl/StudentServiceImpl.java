package com.shubham.student_management.service.impl;

import com.shubham.student_management.dto.StudentDto;
import com.shubham.student_management.entity.Students;
import com.shubham.student_management.mapper.StudentMapper;
import com.shubham.student_management.repository.StudentRepository;
import com.shubham.student_management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Students student = studentMapper.toEntity(studentDto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    @Override
    public Page<StudentDto> getStudent(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC, "id");

        return studentRepository.findByActiveTrue(pageRequest)
                .map(studentMapper::toDto);
    }

    @Override
    public StudentDto getStudentId(Long id) {
        Students student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found"));
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Students student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found"));

        student.setFirstName(studentDto.firstName());
        student.setLastName(studentDto.lastName());
        student.setEmail(studentDto.email());
        student.setPhoneNumber(studentDto.phoneNumber());
        student.setAddress(studentDto.address());
        student.setActive(studentDto.active());

        return studentMapper.toDto(studentRepository.save(student));
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return studentRepository.existsByEmailIgnoreCaseAndIdNot(email, id);
    }
}
