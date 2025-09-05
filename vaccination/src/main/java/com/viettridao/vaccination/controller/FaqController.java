package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping("/faq")
    public String showFaqForm(Model model) {
        model.addAttribute("pageTitle", "Giải đáp thắc mắc");
        return "supportEmployee/faq-form";
    }
}
