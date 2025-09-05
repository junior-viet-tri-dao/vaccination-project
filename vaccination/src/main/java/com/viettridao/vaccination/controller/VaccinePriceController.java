package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VaccinePriceController {

    @GetMapping("/vaccine-price")
    public String showVaccinePriceManagement(Model model) {
        model.addAttribute("pageTitle", "Quản lý giá vắc xin");
        return "financeEmployee/vaccine-price-management";
    }
}
