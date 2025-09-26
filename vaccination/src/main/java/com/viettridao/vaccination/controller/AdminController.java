package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Khi người dùng truy cập /admin/schedule sẽ hiển thị trang schedule
    @GetMapping("/schedule")
    public String showSchedulePage() {
        return "adminPanel/schedule";
    }
}