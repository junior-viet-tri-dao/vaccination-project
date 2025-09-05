package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EpidemicController {

    @GetMapping("/epidemic")
    public String showDiseaseReport(Model model) {
        model.addAttribute("pageTitle", "Tình hình dịch bệnh tại địa phương");
        return "normalUser/epidemic";
    }
}
