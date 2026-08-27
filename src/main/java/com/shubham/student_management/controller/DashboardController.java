package com.shubham.student_management.controller;

import com.shubham.student_management.service.DashboardService;
import com.shubham.student_management.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final EnrollmentService enrollmentService;
    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("dashboardStats", dashboardService.getDashboardStats());
        model.addAttribute("students", enrollmentService.getRecentlyEnrolledStudents());

        return "dashboard";
    }
}