package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WarehouseController {
    @GetMapping("/warehouse")
    public String showWarehousePage() {
        return "warehouse"; // Trả về template warehouse.html
    }
} 
