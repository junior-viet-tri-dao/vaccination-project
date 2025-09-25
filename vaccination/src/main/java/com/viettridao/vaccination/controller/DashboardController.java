package com.viettridao.vaccination.controller;

import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class DashboardController {

    @GetMapping("/adminpanel/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "adminPanel/dashboard";
    }

    @GetMapping("/employee/dashboard")
    @PreAuthorize("hasAnyRole('DOCTER', 'ADMIN')")
    public String doctorDashboard() {
        return "employee/dashboard";
    }

    @GetMapping("/warehouse/warehouse")
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'ADMIN')")
    public String warehouseDashboard() {
        return "warehouse/warehouse";
    }

    @GetMapping("/support/dashboard")
    @PreAuthorize("hasAnyRole('SUPPORTER', 'ADMIN')")
    public String supportDashboard(Model model, Authentication authentication) {
        // Helper lấy vai trò
        String userRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.replace("ROLE_", ""))
                .findFirst()
                .orElse("");
        model.addAttribute("userRole", userRole);
        return "supportEmployee/dashboard";
    }

    @GetMapping("/finance/dashboard")
    @PreAuthorize("hasAnyRole('FINANCE', 'ADMIN')")
    public String financeDashboard(Model model, Authentication authentication) {
        // Helper lấy vai trò
        String userRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.replace("ROLE_", ""))
                .findFirst()
                .orElse("");
        model.addAttribute("userRole", userRole);
        return "financeEmployee/dashboard";
    }

    @GetMapping("/normalUser/dashboard")
    @PreAuthorize("hasAnyRole('NORMAL_USER', 'ADMIN')")
    public String userDashboard() {
        return "normalUser/dashboard";
    }
}
