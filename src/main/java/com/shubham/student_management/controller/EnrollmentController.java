package com.shubham.student_management.controller;

import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.dto.EnrollmentDto;
import com.shubham.student_management.service.CourseService;
import com.shubham.student_management.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enrollments")
@Slf4j
public class EnrollmentController {

    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/showEnroll")
    public String showEnroll(Model model){

        log.info("Get /enrollments/showEnroll - showing enrollment pages");

        model.addAttribute("enrollmentDto", new EnrollmentDto());
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("studentList", studentService.getAllStudents());
        return "enroll-course";
    }
//    @GetMapping("/enrollmentList")
//    public String enrollmentList() {
//        return "enrollment-student";
//    }


}
