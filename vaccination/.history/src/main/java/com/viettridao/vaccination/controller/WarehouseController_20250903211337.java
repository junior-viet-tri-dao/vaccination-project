package com.viettridao.vaccination.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class WarehouseController {
    @GetMapping("/warehouse")
    public String showWarehousePage() {
        return "warehouse"; // Trả về template warehouse.html
    }
} 
