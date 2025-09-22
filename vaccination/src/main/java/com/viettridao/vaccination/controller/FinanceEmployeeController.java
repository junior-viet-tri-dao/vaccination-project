package com.viettridao.vaccination.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.viettridao.vaccination.dto.request.finance.GiaoDichNhaCungCapRequest;
import com.viettridao.vaccination.dto.request.finance.QuanLyGiaVacXinUpdateRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichKhachHangResponse;
import com.viettridao.vaccination.dto.response.finance.GiaoDichNhaCungCapResponse;
import com.viettridao.vaccination.dto.response.finance.QuanLyGiaVacXinResponse;
import com.viettridao.vaccination.service.BenhNhanService;
import com.viettridao.vaccination.service.GiaoDichKhachHangService;
import com.viettridao.vaccination.service.GiaoDichNccService;
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
	private final GiaoDichNccService giaoDichNccService;

	// --- Vaccine Price ---
	@GetMapping("/vaccine-price")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('VIEW_VACCINE_PRICE')) or hasRole('ADMIN')")
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

	@GetMapping("/vaccine-price/create")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('CREATE_VACCINE_PRICE')) or hasRole('ADMIN')")
	public String showCreateVaccinePriceForm(Model model) {
		model.addAttribute("createRequest", new QuanLyGiaVacXinUpdateRequest());
		model.addAttribute("vaccines", vacXinService.getAllActiveVaccines());
		model.addAttribute("vaccineDataForJs", quanLyGiaVacXinService.buildVaccineDataForJs());
		return "financeEmployee/create-vaccine-price";
	}

	@PostMapping("/vaccine-price/create")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('CREATE_VACCINE_PRICE')) or hasRole('ADMIN')")
	public String createVaccinePrice(@Valid @ModelAttribute("createRequest") QuanLyGiaVacXinUpdateRequest request,
			BindingResult bindingResult, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			return "financeEmployee/create-vaccine-price";
		}

		try {
			quanLyGiaVacXinService.createGiaVacXin(request);
			redirectAttrs.addFlashAttribute("success", "Thêm giá vắc xin thành công!");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", "Thêm thất bại: " + e.getMessage());
			return "redirect:/finance/vaccine-price/create";
		}
		return "redirect:/finance/vaccine-price";
	}

	@GetMapping("/vaccine-price/edit")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('UPDATE_VACCINE_PRICE')) or hasRole('ADMIN')")
	public String showEditForm(@RequestParam("maCode") String maCode, Model model) {
		model.addAttribute("updateRequest", quanLyGiaVacXinService.buildUpdateRequest(maCode));
		return "financeEmployee/edit-vaccine-price";
	}

	@PostMapping("/vaccine-price/edit")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('UPDATE_VACCINE_PRICE')) or hasRole('ADMIN')")
	public String updateVaccinePrice(@Valid @ModelAttribute("updateRequest") QuanLyGiaVacXinUpdateRequest request,
			BindingResult bindingResult, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			return "financeEmployee/edit-vaccine-price";
		}

		try {
			quanLyGiaVacXinService.updateGiaVacXin(request);
			redirectAttrs.addFlashAttribute("success", "Cập nhật giá vắc xin thành công!");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", "Cập nhật thất bại: " + e.getMessage());
			return "redirect:/finance/vaccine-price/edit?maCode=" + request.getMaCode();
		}
		return "redirect:/finance/vaccine-price";
	}

	@PostMapping("/vaccine-price/delete")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('DELETE_VACCINE_PRICE')) or hasRole('ADMIN')")
	public String deleteVaccinePrice(@RequestParam String maCode, RedirectAttributes redirectAttrs) {
		try {
			quanLyGiaVacXinService.deleteByMaCode(maCode);
			redirectAttrs.addFlashAttribute("success", "Xóa vắc xin thành công!");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", "Xóa vắc xin thất bại: " + e.getMessage());
		}
		return "redirect:/finance/vaccine-price";
	}

	// --- Customer Transactions ---
	@GetMapping("/transactions-customer")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('VIEW_CUSTOMER_TRANSACTION')) or hasRole('ADMIN')")
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
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('CREATE_CUSTOMER_TRANSACTION')) or hasRole('ADMIN')")
	public String showCreateForm(@RequestParam(required = false) String maVacXin, Model model) {
		model.addAttribute("transactionRequest", giaoDichKhachHangService.buildCreateRequest(maVacXin));
		model.addAttribute("vaccines", vacXinService.getAllVaccines());
		model.addAttribute("patients", benhNhanService.getAllPatients());
		model.addAttribute("vaccinePriceMap", giaoDichKhachHangService.getVaccinePriceMap());
		return "financeEmployee/create-transaction-customer";
	}

	@PostMapping("/transactions-customer/create")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('CREATE_CUSTOMER_TRANSACTION')) or hasRole('ADMIN')")
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
			return "redirect:/finance/transactions-customer";
		} catch (Exception e) {
			giaoDichKhachHangService.handleCreateError(e, model, request);
			return "financeEmployee/create-transaction-customer";
		}
	}

	@GetMapping("/transactions-customer/update/{soHoaDon}")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('UPDATE_CUSTOMER_TRANSACTION')) or hasRole('ADMIN')")
	public String showEditTransaction(@PathVariable String soHoaDon, Model model) {
		model.addAttribute("transactionRequest", giaoDichKhachHangService.buildUpdateRequest(soHoaDon));
		model.addAttribute("vaccines", vacXinService.getAllVaccines());
		model.addAttribute("patients", benhNhanService.getAllPatients());
		model.addAttribute("vaccinePriceMap", giaoDichKhachHangService.getVaccinePriceMap());
		return "financeEmployee/edit-transaction-customer";
	}

	@PostMapping("/transactions-customer/update")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('UPDATE_CUSTOMER_TRANSACTION')) or hasRole('ADMIN')")
	public String updateTransaction(@Valid @ModelAttribute("transactionRequest") GiaoDichKhachHangRequest request,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("vaccines", vacXinService.getAllVaccines());
			model.addAttribute("patients", benhNhanService.getAllPatients());
			return "financeEmployee/edit-transaction-customer";
		}

		try {
			giaoDichKhachHangService.update(request);
			redirectAttrs.addFlashAttribute("success", "Cập nhật giao dịch thành công!");
		} catch (Exception e) {
			model.addAttribute("error", "Cập nhật thất bại: " + e.getMessage());
			model.addAttribute("transactionRequest", request);
			model.addAttribute("vaccines", vacXinService.getAllVaccines());
			model.addAttribute("patients", benhNhanService.getAllPatients());
			return "financeEmployee/edit-transaction-customer";
		}
		return "redirect:/finance/transactions-customer";
	}

	@PostMapping("/transactions-customer/delete")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('DELETE_CUSTOMER_TRANSACTION')) or hasRole('ADMIN')")
	public String deleteTransaction(@RequestParam String maHoaDon, RedirectAttributes redirectAttrs) {
		try {
			giaoDichKhachHangService.deleteByMaHoaDon(maHoaDon);
			redirectAttrs.addFlashAttribute("success", "Xóa thành công!");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", "Xóa thất bại: " + e.getMessage());
		}
		return "redirect:/finance/transactions-customer";
	}

	// --- Supplier Transactions ---
	@GetMapping("/transactions-supplier")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('VIEW_SUPPLIER_TRANSACTION')) or hasRole('ADMIN')")
	public String transactionsSupplier(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {

		Page<GiaoDichNhaCungCapResponse> pageResult = giaoDichNccService.getAll(PageRequest.of(page, size));

		model.addAttribute("transactions", pageResult.getContent());
		model.addAttribute("currentPage", pageResult.getNumber());
		model.addAttribute("pageSize", pageResult.getSize());
		model.addAttribute("totalPages", pageResult.getTotalPages());

		return "financeEmployee/transaction-supplier";
	}

	@GetMapping("/transactions-supplier/create")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('CREATE_SUPPLIER_TRANSACTION')) or hasRole('ADMIN')")
	public String showCreateTransactionForm(Model model) {
		model.addAttribute("giaoDichRequest", new GiaoDichNhaCungCapRequest());
		return "financeEmployee/create-transaction-supplier";
	}

	@PostMapping("/transactions-supplier/create")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('CREATE_SUPPLIER_TRANSACTION')) or hasRole('ADMIN')")
	public String createTransactionSupplier(@Valid @ModelAttribute("giaoDichRequest") GiaoDichNhaCungCapRequest request,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			return "financeEmployee/create-transaction-supplier";
		}

		try {
			giaoDichNccService.create(request);
			redirectAttrs.addFlashAttribute("success", "Tạo giao dịch thành công!");
		} catch (Exception e) {
			model.addAttribute("error", "Tạo giao dịch thất bại: " + e.getMessage());
			return "financeEmployee/create-transaction-supplier";
		}
		return "redirect:/finance/transactions-supplier";
	}

	@GetMapping("/transactions-supplier/edit/{soHoaDon}")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('UPDATE_SUPPLIER_TRANSACTION')) or hasRole('ADMIN')")
	public String showEditTransactionForm(@PathVariable String soHoaDon, Model model) {
		model.addAttribute("giaoDichRequest", giaoDichNccService.buildUpdateRequest(soHoaDon));
		return "financeEmployee/edit-transaction-supplier";
	}

	@PostMapping("/transactions-supplier/update")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('UPDATE_SUPPLIER_TRANSACTION')) or hasRole('ADMIN')")
	public String updateTransactionSupplier(@Valid @ModelAttribute("giaoDichRequest") GiaoDichNhaCungCapRequest request,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			return "financeEmployee/edit-transaction-supplier";
		}

		try {
			giaoDichNccService.update(request);
			redirectAttrs.addFlashAttribute("success", "Cập nhật giao dịch thành công!");
		} catch (Exception e) {
			model.addAttribute("error", "Cập nhật thất bại: " + e.getMessage());
			return "financeEmployee/edit-transaction-supplier";
		}
		return "redirect:/finance/transactions-supplier";
	}

	@PostMapping("/transactions-supplier/delete")
	@PreAuthorize("(hasRole('FINANCE') and hasAuthority('DELETE_SUPPLIER_TRANSACTION')) or hasRole('ADMIN')")
	public String deleteSupplierTransaction(@RequestParam String soHoaDon, RedirectAttributes redirectAttrs) {
		try {
			giaoDichNccService.softDeleteBySoHoaDon(soHoaDon);
			redirectAttrs.addFlashAttribute("success", "Xóa giao dịch thành công!");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", "Xóa giao dịch thất bại: " + e.getMessage());
		}
		return "redirect:/finance/transactions-supplier";
	}
}
