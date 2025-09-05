package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedbackHighLevelController {

    @GetMapping("/feedback-highlevel")
    public String showHighFeedbackForm(Model model) {
        model.addAttribute("pageTitle", "Phản hồi cấp cao");
        return "normalUser/feedback-highlevel";
    }
}
