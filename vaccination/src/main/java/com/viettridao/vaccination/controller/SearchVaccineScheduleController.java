package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchVaccineScheduleController {

    // Chỉ hiển thị view, không truyền data
    @GetMapping("/vaccine-schedule")
    public String showSchedule() {
        return "vaccine-schedule";  // => /resources/templates/vaccine-schedule.html
    }
}
