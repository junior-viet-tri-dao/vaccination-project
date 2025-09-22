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
	@PreAuthorize("hasRole('DOCTER')")
	public String doctorDashboard() {
		return "employee/dashboard"; 
	}

	@GetMapping("/warehouse/dashboard")
	@PreAuthorize("hasRole('WAREHOUSE')")
	public String warehouseDashboard() {
		return "warehouse/dashboard"; 
	}

	@GetMapping("/support/dashboard")
	@PreAuthorize("hasRole('SUPPORTER')")
	public String supportDashboard() {
		return "support/dashboard"; 
	}

	@GetMapping("/finance/dashboard")
	@PreAuthorize("hasRole('FINANCE')")
	public String financeDashboard() {
		return "finance/dashboard";
	}

	@GetMapping("/user/dashboard")
	@PreAuthorize("hasRole('NORMAL_USER')")
	public String userDashboard() {
		return "user/dashboard"; 
	}
}
