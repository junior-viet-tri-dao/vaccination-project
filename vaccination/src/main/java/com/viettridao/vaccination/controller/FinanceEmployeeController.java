package com.viettridao.vaccination.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.service.VaccinePriceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceEmployeeController {

	private final VaccinePriceService vaccinePriceService;

	@GetMapping("/vaccine-price")
	public String showVaccinePriceManagement(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {

		Page<VaccinePriceResponse> vaccinePrices = vaccinePriceService.getPagedBatches(page, size);

		model.addAttribute("vaccinePrices", vaccinePrices.getContent()); // danh sách
		model.addAttribute("currentPage", page); // trang hiện tại
		model.addAttribute("totalPages", vaccinePrices.getTotalPages()); // tổng số trang
		model.addAttribute("pageSize", size); // giữ thêm size để phân trang
		return "financeEmployee/vaccine-price-management"; // file .html
	}

	@GetMapping("/transactions-customer")
	public String showTransactionCustomer(Model model) {
		return "financeEmployee/transaction-customer";
	}

	@GetMapping("/transactions-supplier")
	public String showTransactionSupplier(Model model) {
		return "financeEmployee/transaction-supplier";
	}

}
