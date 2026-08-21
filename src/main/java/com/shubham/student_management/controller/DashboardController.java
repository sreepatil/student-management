package com.shubham.student_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Map<String, Object> dashboardStats = new HashMap<>();

        dashboardStats.put("totalStudents", 0);
        dashboardStats.put("totalCourses", 0);
        dashboardStats.put("topPerformingCourse", "N/A");
        dashboardStats.put("studentsEnrolledThisMonth", 0);

        model.addAttribute("dashboardStats", dashboardStats);
        model.addAttribute("students", java.util.Collections.emptyList());

        return "dashboard";
    }
}