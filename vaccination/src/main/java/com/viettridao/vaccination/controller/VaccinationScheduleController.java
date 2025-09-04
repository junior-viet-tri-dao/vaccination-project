package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VaccinationScheduleController {

    @GetMapping("/search-schedule")
    public String view(Model model) {
        model.addAttribute("pageTitle", "Quản lý lịch tiêm chủng");
        return "normalUser/search-vaccination-schedule";
    }

    @PostMapping("/search-schedule")
    public String save(RedirectAttributes ra) {
        ra.addFlashAttribute("msg", "Đã lưu lịch tiêm chủng (demo).");
        return "redirect:/search-schedule";
    }

    @GetMapping("/register-center")
    public String register() {
        return "normalUser/vaccine-register-center";
    }
}

