package com.shubham.student_management.controller;

import com.shubham.student_management.dto.EnrollmentDto;
import com.shubham.student_management.dto.EnrollmentSummaryDto;
import com.shubham.student_management.service.CourseService;
import com.shubham.student_management.service.EnrollmentService;
import com.shubham.student_management.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enrollments")
@Slf4j
public class EnrollmentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/showEnroll")
    public String showEnroll(Model model){

        log.info("Get /enrollments/showEnroll - showing enrollment pages");

        model.addAttribute("enrollmentDto", new EnrollmentDto());
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("studentList", studentService.getAllStudents());
        return "enroll-course";
    }

    @GetMapping("/enrollmentList")
    public String enrollmentList(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "true") boolean active,
            Model model) {

        Page<EnrollmentSummaryDto> students =
                enrollmentService.getEnrolledStudents(page, size, active);

        model.addAttribute("students", students);
        model.addAttribute("active", active);

        return "enrolled-students";
    }

    @PostMapping("/enrollCourse")
    public String enrollCourse(@Valid @ModelAttribute("enrollmentDto")
                               EnrollmentDto enrollmentDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            model.addAttribute("courseList", courseService.getAllCourses());
            model.addAttribute("studentList", studentService.getAllStudents());

            return "enroll-course";
        }

        enrollmentService.enrollmentStudentToCourses(enrollmentDto);
        redirectAttributes.addFlashAttribute("message", "Enrollment Successful !!");

        return "redirect:/enrollments/enrollmentList";    }

    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(
            @PathVariable Long id,
            Model model) {

        EnrollmentSummaryDto enrollmentSummaryDto =
                enrollmentService.findEnrolledStudentCourseDetails(id);

        model.addAttribute("enrollmentSummaryDto", enrollmentSummaryDto);

        return "enrollment-details";
    }
}
