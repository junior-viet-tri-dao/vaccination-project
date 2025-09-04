package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WarehouseController {
    @GetMapping("/warehouse/warehouse")
    public String showWarehousePage() {
        return "warehouse/warehouse"; // Trả về template warehouse.html
    }

      @GetMapping("/warehouse/importvaccine")
    public String showImportVaccineForm() {
        return "warehouse/import-vaccine"; // Tên file HTML trong /templates
    } 
} 
