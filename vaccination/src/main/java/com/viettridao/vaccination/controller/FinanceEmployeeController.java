package com.viettridao.vaccination.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viettridao.vaccination.dto.request.finance.GiaoDichKhachHangRequest;
import com.viettridao.vaccination.dto.request.finance.QuanLyGiaVacXinUpdateRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichKhachHangResponse;
import com.viettridao.vaccination.dto.response.finance.QuanLyGiaVacXinResponse;
import com.viettridao.vaccination.service.BenhNhanService;
import com.viettridao.vaccination.service.GiaoDichKhachHangService;
import com.viettridao.vaccination.service.QuanLyGiaVacXinService;
import com.viettridao.vaccination.service.VacXinService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceEmployeeController {

	private final VacXinService vacXinService;
	private final BenhNhanService benhNhanService;
	private final QuanLyGiaVacXinService quanLyGiaVacXinService;
	private final GiaoDichKhachHangService giaoDichKhachHangService;

	// --- Vaccine Price ---
	@GetMapping("/vaccine-price")
	public String showVaccinePriceManagement(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {

		Page<QuanLyGiaVacXinResponse> vaccinePrices = quanLyGiaVacXinService
				.getAllGiaVacXinHienTai(PageRequest.of(page, size));

		model.addAttribute("vaccinePrices", vaccinePrices.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", vaccinePrices.getTotalPages());
		model.addAttribute("pageSize", size);

		return "financeEmployee/vaccine-price";
	}

	@GetMapping("/vaccine-price/edit")
	public String showEditForm(@RequestParam("maCode") String maCode, Model model) {
		QuanLyGiaVacXinResponse response = quanLyGiaVacXinService.getByMaCode(maCode);

		model.addAttribute("updateRequest", new QuanLyGiaVacXinUpdateRequest(response.getMaCode(), response.getNamSX(),
				response.getDonVi(), response.getGia()));

		return "financeEmployee/edit-vaccine-price";
	}

	@PostMapping("/vaccine-price/edit")
	public String updateVaccinePrice(@Valid @ModelAttribute("updateRequest") QuanLyGiaVacXinUpdateRequest request,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "financeEmployee/edit-vaccine-price";
		}

		try {
			quanLyGiaVacXinService.updateGiaVacXin(request);
			redirectAttributes.addFlashAttribute("success", "Cập nhật giá vắc xin thành công!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Cập nhật thất bại: " + e.getMessage());
			return "redirect:/finance/vaccine-price/edit?maCode=" + request.getMaCode();
		}

		return "redirect:/finance/vaccine-price";
	}

	@PostMapping("/vaccine-price/delete")
	public String deleteVaccinePrice(@RequestParam String maCode, RedirectAttributes redirectAttrs) {
		try {
			quanLyGiaVacXinService.deleteByMaCode(maCode);
			redirectAttrs.addFlashAttribute("success", "Xóa vắc xin thành công!");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", "Xóa vắc xin thất bại: " + e.getMessage());
		}
		return "redirect:/finance/vaccine-price";
	}

	// Hiển thị danh sách giao dịch
	@GetMapping("/transactions-customer")
	public String showCustomerTransactions(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {

		Page<GiaoDichKhachHangResponse> transactionsPage = giaoDichKhachHangService.getAll(PageRequest.of(page, size));

		model.addAttribute("transactions", transactionsPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", transactionsPage.getTotalPages());
		model.addAttribute("pageSize", size);

		return "financeEmployee/transaction-customer";
	}

	@GetMapping("/transactions-customer/create")
	public String showCreateForm(Model model) {
	    if (!model.containsAttribute("transactionRequest")) {
	        GiaoDichKhachHangRequest dto = new GiaoDichKhachHangRequest();
	        dto.setNgayHD(LocalDateTime.now());
	        model.addAttribute("transactionRequest", dto);
	    }

	    model.addAttribute("vaccines", vacXinService.getAllVaccines());
	    model.addAttribute("patients", benhNhanService.getAllPatients());

	    return "financeEmployee/create-transaction-customer";
	}

	@PostMapping("/transactions-customer/create")
	public String createTransaction(@Valid @ModelAttribute("transactionRequest") GiaoDichKhachHangRequest request,
	        BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("vaccines", vacXinService.getAllVaccines());
	        model.addAttribute("patients", benhNhanService.getAllPatients());
	        return "financeEmployee/create-transaction-customer";
	    }

	    try {
	        giaoDichKhachHangService.create(request);
	        redirectAttrs.addFlashAttribute("success", "Tạo giao dịch thành công!");
	    } catch (Exception e) {
	        model.addAttribute("transactionRequest", request);
	        model.addAttribute("vaccines", vacXinService.getAllVaccines());
	        model.addAttribute("patients", benhNhanService.getAllPatients());
	        model.addAttribute("error", "Tạo giao dịch thất bại: " + e.getMessage());
	        return "financeEmployee/create-transaction-customer";
	    }

	    return "redirect:/finance/transactions-customer";
	}
	
	@GetMapping("/transactions-customer/update/{soHoaDon}")
	public String showEditTransaction(@PathVariable("soHoaDon") String soHoaDon, Model model) {
	    GiaoDichKhachHangResponse transaction = giaoDichKhachHangService.getByMaHoaDon(soHoaDon);

	    GiaoDichKhachHangRequest request = new GiaoDichKhachHangRequest();
	    request.setNgayHD(transaction.getNgayHD());
	    request.setSoHoaDon(transaction.getSoHoaDon());
	    request.setMaVacXin(transaction.getMaVacXin());
	    request.setSoLuong(transaction.getSoLuong());
	    request.setTenKhachHang(transaction.getTenKhachHang());
	    request.setGia(transaction.getGia());

	    model.addAttribute("transactionRequest", request);
	    model.addAttribute("vaccines", vacXinService.getAllVaccines());
	    model.addAttribute("patients", benhNhanService.getAllPatients());

	    return "financeEmployee/edit-transaction-customer";
	}

	// Xử lý update giao dịch
	@PostMapping("/transactions-customer/update")
	public String updateTransaction(@Valid @ModelAttribute("transactionRequest") GiaoDichKhachHangRequest request,
	                                BindingResult bindingResult,
	                                Model model,
	                                RedirectAttributes redirectAttrs) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("vaccines", vacXinService.getAllVaccines());
	        model.addAttribute("patients", benhNhanService.getAllPatients());
	        return "financeEmployee/edit-transaction-customer";
	    }

	    try {
	        giaoDichKhachHangService.update(request);
	        redirectAttrs.addFlashAttribute("success", "Cập nhật giao dịch thành công!");
	    } catch (Exception e) {
	        model.addAttribute("transactionRequest", request);
	        model.addAttribute("vaccines", vacXinService.getAllVaccines());
	        model.addAttribute("patients", benhNhanService.getAllPatients());
	        model.addAttribute("error", "Cập nhật thất bại: " + e.getMessage());
	        return "financeEmployee/edit-transaction-customer";
	    }

	    return "redirect:/finance/transactions-customer";
	}


	@PostMapping("/transactions-customer/delete")
	public String deleteTransaction(@RequestParam String maHoaDon, RedirectAttributes redirectAttrs) {
		try {
			giaoDichKhachHangService.deleteByMaHoaDon(maHoaDon);
			redirectAttrs.addFlashAttribute("success", "Xóa thành công!");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", "Xóa thất bại: " + e.getMessage());
		}
		return "redirect:/finance/transactions-customer";
	}
}
