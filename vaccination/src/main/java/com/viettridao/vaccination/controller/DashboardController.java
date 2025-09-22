package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class DashboardController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "adminPanel/dashboard";
    }

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard() {
        return "doctor/dashboard";
    }

    @GetMapping("/warehouse/dashboard")
    public String warehouseDashboard() {
        return "warehouse/dashboard";
    }

    @GetMapping("/support/dashboard")
    public String supportDashboard() {
        return "support/dashboard";
    }

    @GetMapping("/finance/dashboard")
    public String financeDashboard() {
        return "finance/dashboard";
    }

    @GetMapping("/normalUser/dashboard")
    public String userDashboard() {
        return "normalUser/dashboard";
    }
}
