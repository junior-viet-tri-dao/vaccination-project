package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileNormalUserController {

    // Xem hồ sơ cá nhân
    @GetMapping("/view-profile")
    public String viewHistory(Model model) {
        model.addAttribute("pageTitle", "Hồ sơ tiêm phòng");
        return "normalUser/view-profile";
    }

    // Sửa thông tin cá nhân
    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        model.addAttribute("pageTitle", "Sửa đổi thông tin cá nhân");
        return "normalUser/edit-profile";
    }
}
