package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

//    // Hiển thị giao diện Dashboard khi người dùng truy cập /dashboard
//    @GetMapping("/dashboard")
//    public String showDashboard(Model model) {
//        // Nếu cần, có thể thêm attribute cho dashboard ở đây:
//        // model.addAttribute("pageTitle", "Dashboard Trang quản trị");
//        return "adminPanel/dashboard";
//    }

    // Khi người dùng truy cập /admin/schedule sẽ hiển thị trang schedule
    @GetMapping("/schedule")
    public String showSchedulePage() {
        return "adminPanel/schedule";
    }
}