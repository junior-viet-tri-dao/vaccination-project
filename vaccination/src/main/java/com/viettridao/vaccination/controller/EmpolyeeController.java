package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpolyeeController {

      @GetMapping("/employee/view")
    public String showEmployee(Model model) {
        model.addAttribute("tab", "view");
        return "employee/view"; 
    }

      @GetMapping("/employee/update")
    public String updateEmployee(Model model) {
        model.addAttribute("tab", "update");
        return "employee/update"; 
    } 


    @GetMapping("/employee/prescripe")
    public String prescripeEmpoyee(Model model) {
        model.addAttribute("tab", "prescripe");
        return "employee/prescripe"; 
    } 
}
    

