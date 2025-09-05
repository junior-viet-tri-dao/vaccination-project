package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReminderController {

    @GetMapping("/reminder")
    public String showReminderForm(Model model) {
        model.addAttribute("pageTitle", "Nhắc lịch tiêm chủng");
        return "supportEmployee/reminder-form";
    }
}
