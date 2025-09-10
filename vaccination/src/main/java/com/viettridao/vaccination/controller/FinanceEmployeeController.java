package com.viettridao.vaccination.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.service.VaccineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceEmployeeController {

	private final VaccineService vaccineService;


	@GetMapping("/vaccine-price")
	public String showVaccinePriceManagement(@RequestParam(defaultValue = "0") int page,
	                                         @RequestParam(defaultValue = "10") int size,
	                                         Model model) {

	    Page<VaccinePriceResponse> vaccinePrices = vaccineService.getAllVaccinePrices(page, size);

	    model.addAttribute("vaccinePrices", vaccinePrices.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", vaccinePrices.getTotalPages());
	    model.addAttribute("pageSize", size);

	    return "financeEmployee/vaccine-price"; // view thymeleaf
	}

}