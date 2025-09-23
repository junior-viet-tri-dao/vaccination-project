package com.viettridao.vaccination.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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

	@GetMapping("/warehouse/dashboard")
	@PreAuthorize("hasAnyRole('WAREHOUSE', 'ADMIN')")
	public String warehouseDashboard() {
		return "warehouse/dashboard"; 
	}

	@GetMapping("/support/dashboard")
	@PreAuthorize("hasAnyRole('SUPPORTER', 'ADMIN')")
	public String supportDashboard() {
		return "support/dashboard"; 
	}

	@GetMapping("/finance/dashboard")
	@PreAuthorize("hasAnyRole('FINANCE', 'ADMIN')")
	public String financeDashboard() {
		return "finance/dashboard";
	}

	@GetMapping("/normalUser/dashboard")
	@PreAuthorize("hasAnyRole('NORMAL_USER', 'ADMIN')")
	public String userDashboard() {
		return "normalUser/dashboard"; 
	}
}
