package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WarehouseController {
    @GetMapping("/warehouse/warehouse")
    public String showWarehousePage(Model model) {
        model.addAttribute("tab", "warehouse");
        return "warehouse/warehouse"; // Trả về template warehouse.html
    }

      @GetMapping("/warehouse/importvaccine")
    public String showImportVaccineForm(Model model) {
        model.addAttribute("tab", "import");
        return "warehouse/importvaccine"; 
    } 
} 
