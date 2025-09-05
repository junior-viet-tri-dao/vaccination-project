package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedbackController {

    @GetMapping("/feedback")
    public String showFeedbackForm(Model model) {
        model.addAttribute("pageTitle", "Gửi phản hồi sau tiêm phòng");
        return "normalUser/feedback-form";
    }
}
