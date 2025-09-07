package com.viettridao.vaccination.controller;

import com.viettridao.vaccination.dto.request.finance.UpdateVaccinePriceRequest;
import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.service.VaccinePriceService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

		// ✅ thêm request trống cho form sửa
		if (!model.containsAttribute("vaccinePriceRequest")) {
			model.addAttribute("vaccinePriceRequest", new UpdateVaccinePriceRequest());
		}

		return "financeEmployee/vaccine-price"; // file .html
	}

	@PostMapping("/vaccine-price/delete")
	public String deleteBatch(@RequestParam String batchId, RedirectAttributes redirectAttributes) {
		vaccinePriceService.softDeleteBatch(batchId);
		redirectAttributes.addFlashAttribute("success", "Xóa thành công");
		return "redirect:/finance/vaccine-price";
	}

	@PostMapping("/vaccine-price/update")
	public String updateVaccinePrice(
			@Valid @ModelAttribute("vaccinePriceRequest") UpdateVaccinePriceRequest request,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (bindingResult.hasErrors()) {
			// Trả lại modal với lỗi
			model.addAttribute("vaccinePriceRequest", request);
			return "financeEmployee/edit-vaccine-price";
		}

		vaccinePriceService.update(request.getBatchId(), request);
		redirectAttributes.addFlashAttribute("success", "Cập nhật giá vắc xin thành công!");


		return "redirect:/finance/vaccine-price";
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
