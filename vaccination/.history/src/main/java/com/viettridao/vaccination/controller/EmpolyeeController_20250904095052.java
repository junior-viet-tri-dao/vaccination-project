package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpolyeeController {

      @GetMapping("/employee/view")
    public String showWarehousePage(Model model) {
        model.addAttribute("tab", "warehouse");
        return "warehouse/warehouse"; 
    }

      @GetMapping("/warehouse/update")
    public String showImportVaccineForm(Model model) {
        model.addAttribute("tab", "import");
        return "warehouse/importvaccine"; 
    } 


    @GetMapping("/warehouse/exportvaccine")
    public String showExportVaccineForm(Model model) {
        model.addAttribute("tab", "export");
        return "warehouse/exportvaccine"; 
    } 
}
    

