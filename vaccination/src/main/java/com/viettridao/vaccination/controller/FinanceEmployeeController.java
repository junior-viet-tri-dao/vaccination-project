package com.viettridao.vaccination.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.service.BangGiaVacXinService;
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
	private final BangGiaVacXinService bangGiaVacXinService;

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

	@GetMapping("/vaccine-price/create")
	public String showCreateVaccinePriceForm(Model model) {
		QuanLyGiaVacXinUpdateRequest createRequest = new QuanLyGiaVacXinUpdateRequest();
		model.addAttribute("createRequest", createRequest);

		List<VacXinEntity> vaccines = vacXinService.getAllActiveVaccines();
		model.addAttribute("vaccines", vaccines);

		List<Map<String, Object>> vaccineDataForJs = vaccines.stream().map(vx -> {
			LoVacXinEntity lo = vx.getLoVacXins().stream().findFirst().orElse(null);
			Integer gia = bangGiaVacXinService.findByVacXinIdOrderByHieuLucTuDesc(vx.getId()).stream().findFirst()
					.map(BangGiaVacXinEntity::getGia).orElse(0);

			Map<String, Object> map = new HashMap<>();
			map.put("maCode", vx.getMaCode());
			map.put("donVi", lo != null ? lo.getDonVi() : "");
			map.put("namSX", lo != null ? lo.getNgaySanXuat() : null);
			map.put("gia", gia);
			return map;
		}).collect(Collectors.toList());

		model.addAttribute("vaccineDataForJs", vaccineDataForJs);
		return "financeEmployee/create-vaccine-price";
	}

	@PostMapping("/vaccine-price/create")
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
	public String showCreateForm(@RequestParam(required = false) String maVacXin, Model model) {
		GiaoDichKhachHangRequest dto = new GiaoDichKhachHangRequest();
		dto.setNgayHD(LocalDateTime.now());

		// Lấy danh sách vắc xin
		List<VacXinEntity> vaccines = vacXinService.getAllVaccines();
		model.addAttribute("vaccines", vaccines);

		// Map mã vắc xin -> giá
		Map<String, Integer> vaccinePriceMap = vaccines.stream().collect(Collectors.toMap(VacXinEntity::getMaCode,
				v -> giaoDichKhachHangService.getGiaTheoMaVacXin(v.getMaCode())));
		model.addAttribute("vaccinePriceMap", vaccinePriceMap);

		// Nếu có mã vắc xin truyền vào, set giá
		if (maVacXin != null && !maVacXin.isEmpty()) {
			dto.setMaVacXin(maVacXin);
			dto.setGia(vaccinePriceMap.getOrDefault(maVacXin, 0));
		}

		model.addAttribute("transactionRequest", dto);
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
			return "redirect:/finance/transactions-customer";
		} catch (IllegalArgumentException e) {
			// Phân loại lỗi theo field
			String msg = e.getMessage();
			if (msg.contains("MaLo")) {
				model.addAttribute("maLoError", msg);
			} else if (msg.contains("SoHoaDon")) {
				model.addAttribute("soHoaDonError", msg);
			} else {
				model.addAttribute("globalError", msg);
			}

			model.addAttribute("transactionRequest", request);
			model.addAttribute("vaccines", vacXinService.getAllVaccines());
			model.addAttribute("patients", benhNhanService.getAllPatients());
			return "financeEmployee/create-transaction-customer";
		}
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
		request.setGia(transaction.getGia() != null ? transaction.getGia() : 0);

		model.addAttribute("transactionRequest", request);
		model.addAttribute("vaccines", vacXinService.getAllVaccines());
		model.addAttribute("patients", benhNhanService.getAllPatients());

		// ⚡ Add vaccinePriceMap
		Map<String, Integer> vaccinePriceMap = vacXinService.getAllVaccines().stream().collect(Collectors
				.toMap(VacXinEntity::getMaCode, v -> giaoDichKhachHangService.getGiaTheoMaVacXin(v.getMaCode())));
		model.addAttribute("vaccinePriceMap", vaccinePriceMap);

		return "financeEmployee/edit-transaction-customer";
	}

	// Xử lý update giao dịch
	@PostMapping("/transactions-customer/update")
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

	@GetMapping("/transactions-supplier")
	public String transactionsSupplier(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<GiaoDichNhaCungCapResponse> pageResult = giaoDichNccService.getAll(pageable);

		model.addAttribute("transactions", pageResult.getContent());
		model.addAttribute("currentPage", pageResult.getNumber());
		model.addAttribute("pageSize", pageResult.getSize());
		model.addAttribute("totalPages", pageResult.getTotalPages());

		return "financeEmployee/transaction-supplier";
	}

	@GetMapping("/transactions-supplier/create")
	public String showCreateTransactionForm(Model model) {
		model.addAttribute("giaoDichRequest", new GiaoDichNhaCungCapRequest());
		return "financeEmployee/create-transaction-supplier";
	}

	@PostMapping("/transactions-supplier/create")
	public String createTransaction(@Valid @ModelAttribute("giaoDichRequest") GiaoDichNhaCungCapRequest request,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			return "financeEmployee/create-transaction-supplier";
		}

		try {
			giaoDichNccService.create(request); // Service sẽ xử lý map và lưu entity
			redirectAttrs.addFlashAttribute("success", "Tạo giao dịch thành công!");
		} catch (Exception e) {
			model.addAttribute("error", "Tạo giao dịch thất bại: " + e.getMessage());
			return "financeEmployee/create-transaction-supplier";
		}

		return "redirect:/finance/transactions-supplier";
	}

	@GetMapping("/transactions-supplier/edit/{soHoaDon}")
	public String showEditTransactionForm(@PathVariable String soHoaDon, Model model) {
		GiaoDichNhaCungCapResponse transaction = giaoDichNccService.getBySoHoaDon(soHoaDon);

		GiaoDichNhaCungCapRequest request = new GiaoDichNhaCungCapRequest();
		request.setSoHoaDon(transaction.getSoHoaDon());
		request.setNgay(transaction.getNgay());
		request.setMaLo(transaction.getMaLo());
		request.setMaVacXin(transaction.getMaVacXin());
		request.setTenVacXin(transaction.getTenVacXin());
		request.setSoLuong(transaction.getSoLuong());
		request.setGia(transaction.getGia());
		request.setTenNhaCungCap(transaction.getNhaCungCap());

		model.addAttribute("giaoDichRequest", request);
		return "financeEmployee/edit-transaction-supplier";
	}

	@PostMapping("/transactions-supplier/update")
	public String updateTransaction(@Valid @ModelAttribute("giaoDichRequest") GiaoDichNhaCungCapRequest request,
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
