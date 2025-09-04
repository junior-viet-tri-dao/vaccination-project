package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Khi người dùng truy cập /dashboard thì hiển thị giao diện Dashboard
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "adminPanel/dashboard";
    }
}

