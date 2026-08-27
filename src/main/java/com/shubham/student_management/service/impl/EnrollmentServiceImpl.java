package com.shubham.student_management.service.impl;

import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.dto.EnrollmentDto;
import com.shubham.student_management.dto.EnrollmentSummaryDto;
import com.shubham.student_management.entity.Courses;
import com.shubham.student_management.entity.Enrollment;
import com.shubham.student_management.entity.Students;
import com.shubham.student_management.mapper.CourseMapper;
import com.shubham.student_management.repository.CourseRepository;
import com.shubham.student_management.repository.EnrollmentRepository;
import com.shubham.student_management.repository.StudentRepository;
import com.shubham.student_management.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public void enrollmentStudentToCourses(EnrollmentDto enrollmentDto) {
        log.info("Request from enrollmentStudentToCourses");

        Students student = studentRepository.findById(enrollmentDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student Id not found"));

        for (Long courseId : enrollmentDto.getCourseIds()){
            Courses course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDto.getStudentId(), courseId)){
                continue;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);

            student.getEnrollment().add(enrollment);
            course.getEnrollment().add(enrollment);

            enrollmentRepository.save(enrollment);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EnrollmentSummaryDto> getEnrolledStudents(
            int page, int size, boolean active) {

        PageRequest pageRequest =
                PageRequest.of(page, size, Sort.Direction.DESC, "id");

        return studentRepository.findEnrolledStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();

                    dto.setStudentId(student.getId());
                    dto.setStudentName(
                            student.getFirstName() + " " + student.getLastName()
                    );
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollment().size());

                    BigDecimal totalFee = student.getEnrollment()
                            .stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    dto.setTotalFee(totalFee);

                    return dto;
                });
    }

    @Override
    public EnrollmentSummaryDto findEnrolledStudentCourseDetails(Long studentId) {

        return studentRepository.findEnrolledStudentCourseDetails(studentId).
                map(students -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();
                    dto.setStudentId(students.getId());
                    dto.setStudentName(students.getFirstName() + " " + students.getLastName());
                    dto.setEmail(students.getEmail());

                    dto.setCourseCount(students.getEnrollment().size());
                    BigDecimal totalFee = (students.getEnrollment().stream()
                            .map(enrollment ->
                                    enrollment.getCourse().getFee())
                            .filter(fee -> fee != null))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    dto.setTotalFee(totalFee);

                    List<CourseDto> courseList = students.getEnrollment().stream().map(
                            enrollment -> enrollment.getCourse())
                            .map(courses -> courseMapper.toDto(courses))
                            .collect(Collectors.toList());

                    dto.setCourseList(courseList);

                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Student Not Found "));
    }
}
