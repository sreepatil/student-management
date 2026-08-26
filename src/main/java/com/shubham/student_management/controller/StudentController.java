package com.shubham.student_management.controller;

import com.shubham.student_management.dto.StudentDto;
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
@RequestMapping("/students")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/new")
    public String showCreateStudent(Model model) {

        model.addAttribute(
                "studentDto",
                new StudentDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        true
                )
        );

        return "add-student";
    }

    @GetMapping("/list")
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "true") boolean active,
            Model model) {

        Page<StudentDto> students =
                studentService.getStudents(page, size, active);

        model.addAttribute("students", students);
        model.addAttribute("active", active);

        return "students";
    }

    @PostMapping("/save")
    public String createStudent(
            @Valid @ModelAttribute("studentDto") StudentDto studentDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        log.info("Post/save - create student request received");

        if (bindingResult.hasErrors()) {
            return "add-student";
        }

        if (studentService.existsByEmail(studentDto.email())) {
            log.error("Post/save - email must be unique");

            bindingResult.rejectValue(
                    "email",
                    null,
                    "Email must be unique"
            );

            return "add-student";
        }

        studentService.createStudent(studentDto);

        redirectAttributes.addFlashAttribute(
                "message",
                "Student is created successfully"
        );

        return "redirect:/students/list";
    }

    @GetMapping("/{id}")
    public String getStudent(
            @PathVariable Long id,
            Model model) {

        StudentDto student = studentService.getStudentId(id);

        model.addAttribute("student", student);

        return "view-student";
    }

    @GetMapping("/{id}/edit")
    public String editStudent(
            @PathVariable Long id,
            Model model) {

        StudentDto student = studentService.getStudentId(id);

        model.addAttribute("studentDto", student);

        return "edit-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(
            @PathVariable Long id,
            @Valid @ModelAttribute("studentDto") StudentDto studentDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "edit-student";
        }

        if (studentService.existsByEmailAndIdNot(
                studentDto.email(), id)) {

            bindingResult.rejectValue(
                    "email",
                    null,
                    "Email must be unique"
            );

            return "edit-student";
        }

        studentService.updateStudent(id, studentDto);

        redirectAttributes.addFlashAttribute(
                "message",
                "Student updated successfully"
        );

        return "redirect:/students/list";
    }

}