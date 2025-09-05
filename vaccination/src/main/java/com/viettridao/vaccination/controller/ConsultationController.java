package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultationController {

    @GetMapping("/consultation")
    public String showConsultationForm(Model model) {
        model.addAttribute("pageTitle", "Tư vấn tiêm chủng");
        return "supportEmployee/consultation-form";
    }
}
