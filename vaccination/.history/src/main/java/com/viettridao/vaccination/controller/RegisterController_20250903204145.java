package com.viettridao.vaccination.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class RegisterController {
    @GetMapping("/register")
    public String RegisterPage() {
        return "register"; // trả về file login.html
    }
}
