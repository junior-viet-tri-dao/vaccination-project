package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpolyeeController {

      @GetMapping("/employee/view")
    public String showEmployee(Model model) {
        model.addAttribute("tab", "view");
        return "/employee/view"; 
    }

      @GetMapping("/employee/update")
    public String updateEmployee(Model model) {
        model.addAttribute("tab", "import");
        return "/employee/update"; 
    } 


    @GetMapping("/employee/presripe")
    public String presripeEmpoyee(Model model) {
        model.addAttribute("tab", "export");
        return "warehouse/exportvaccine"; 
    } 
}
    

