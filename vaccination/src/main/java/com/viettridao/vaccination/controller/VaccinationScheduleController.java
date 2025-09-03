package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VaccinationScheduleController {

    @GetMapping("/schedule")
    public String view(Model model) {
        model.addAttribute("pageTitle", "Quản lý lịch tiêm chủng");
        return "schedule"; // templates/schedule.html
    }

    // Demo lưu (chưa bind model). Sau này map DTO/Entity tuỳ bạn.
    @PostMapping("/schedule")
    public String save(RedirectAttributes ra) {
        ra.addFlashAttribute("msg", "Đã lưu lịch tiêm chủng (demo).");
        return "redirect:/schedule";
    }
}

