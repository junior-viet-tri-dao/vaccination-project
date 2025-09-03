package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RegisterController {
    @GetMapping("/register")
    public String RegisterPage() {
        return "register";
    }
}
