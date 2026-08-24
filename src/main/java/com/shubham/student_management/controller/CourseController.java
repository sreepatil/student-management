package com.shubham.student_management.controller;

import com.shubham.student_management.dto.CourseDto;
import com.shubham.student_management.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/new")
    public String ShowCreateCourse(Model model){
        model.addAttribute("courseDto", new CourseDto(null, null, null, null, null, null, true));
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            Model model) {

        Page<CourseDto> courses = courseService.getCourses(page, size);

        model.addAttribute("courses", courses);

        return "courses";
    }


    @PostMapping
    public String createCourse(@Valid @ModelAttribute("courseDto")
                                   CourseDto courseDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "add-course";
        }

        if (courseService.existsByCourseCode(courseDto.courseCode())){
            bindingResult.rejectValue("courseCode", null, "Code must be unique");
            return "add-course";
        }

        courseService.createCourse(courseDto);
        redirectAttributes.addFlashAttribute(
                "message",
                "Course is created Successfully");

        return "redirect:/course/list";
    }


    @GetMapping("/{id}")
    public String getCourseId(@PathVariable Long id, Model model){
        CourseDto course = courseService.getCourseId(id);
        model.addAttribute("course" ,course);

        return "view-course";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model){
        CourseDto course = courseService.getCourseId(id);
        model.addAttribute("courseDto" ,course);

        return "edit-course";
    }

    @PostMapping("/{id}/edit")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("courseDto")
                               CourseDto courseDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "edit-course";
        }

        if (courseService.existsByCourseCodeAndIdNot(courseDto.courseCode(), id)){
            bindingResult.rejectValue("courseCode", null, "Code must be unique");
            return "edit-course";
        }

        courseService.updateCourse(id, courseDto);
        redirectAttributes.addFlashAttribute(
                "message",
                "Course is Updated Successfully !!");

        return "redirect:/course/list";
    }


}
