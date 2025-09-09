package com.viettridao.vaccination.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.viettridao.vaccination.dto.request.finance.*;
import com.viettridao.vaccination.dto.response.finance.TransactionSupplierResponse;
import com.viettridao.vaccination.service.SupplierService;
import org.springframework.data.domain.Page;
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

import com.viettridao.vaccination.dto.response.finance.TransactionCustomerResponse;
import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.service.InvoiceService;
import com.viettridao.vaccination.service.PatientService;
import com.viettridao.vaccination.service.VaccinePriceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceEmployeeController {

	private final VaccinePriceService vaccinePriceService;
	private final InvoiceService invoiceService;
	private final PatientService patientService;
	private final SupplierService invoiceSupplierService;

	@GetMapping("/vaccine-price")
	public String showVaccinePriceManagement(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {

		Page<VaccinePriceResponse> vaccinePrices = vaccinePriceService.getPagedBatches(page, size);

		model.addAttribute("vaccinePrices", vaccinePrices.getContent()); // danh sách
		model.addAttribute("currentPage", page); // trang hiện tại
		model.addAttribute("totalPages", vaccinePrices.getTotalPages()); // tổng số trang
		model.addAttribute("pageSize", size); // giữ thêm size để phân trang


		return "financeEmployee/vaccine-price"; // file .html
	}


	 @GetMapping("/vaccine-price/edit")
	    public String editVaccinePriceForm(@RequestParam String batchId, Model model) {
	        UpdateVaccinePriceRequest request = vaccinePriceService.getByBatchId(batchId);
	        model.addAttribute("vaccinePriceRequest", request);
	        return "financeEmployee/edit-vaccine-price";
	    }

	    // --- Xử lý cập nhật ---
	    @PostMapping("/vaccine-price/update")
	    public String updateVaccinePrice(@Valid @ModelAttribute("vaccinePriceRequest") UpdateVaccinePriceRequest request,
	                                     BindingResult bindingResult,
	                                     RedirectAttributes redirectAttributes,
	                                     Model model) {

	        if (bindingResult.hasErrors()) {
	            model.addAttribute("vaccinePriceRequest", request);
	            return "financeEmployee/edit-vaccine-price"; // trả về trang sửa nếu có lỗi
	        }

	        vaccinePriceService.update(request.getBatchId(), request);
	        redirectAttributes.addFlashAttribute("success", "Cập nhật giá vắc xin thành công!");
	        return "redirect:/finance/vaccine-price";
	    }
	
	
	@PostMapping("/vaccine-price/delete")
	public String deleteBatch(@RequestParam String batchId, RedirectAttributes redirectAttributes) {
		vaccinePriceService.softDeleteBatch(batchId);
		redirectAttributes.addFlashAttribute("success", "Xóa thành công");
		return "redirect:/finance/vaccine-price";
	}

	// --- Quản lý giao dịch khách hàng ---
	@GetMapping("/transactions-customer")
	public String showTransactionCustomer(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size,
			Model model) {

		Page<TransactionCustomerResponse> transactions = invoiceService.getAllTransactions(page, size);

		model.addAttribute("transactions", transactions.getContent()); // danh sách hóa đơn
		model.addAttribute("currentPage", page); // trang hiện tại
		model.addAttribute("totalPages", transactions.getTotalPages()); // tổng số trang
		model.addAttribute("pageSize", size); // số bản ghi mỗi trang

		return "financeEmployee/transaction-customer"; // trỏ đến view Thymeleaf
	}

	// --- Hiển thị form thêm mới ---
	@GetMapping("/transactions-customer/new")
	public String showCreateForm(Model model) {
		model.addAttribute("transactionRequest", new CreateTransactionCustomerRequest());
		model.addAttribute("vaccines", invoiceService.getActiveVaccines());

		List<String> patientCodes = patientService.getAllPatientCodes();
		model.addAttribute("patientCodes", patientCodes);

		// Tạo map patientCode -> patientName
		Map<String, String> patientNameMap = patientCodes.stream()
				.collect(Collectors.toMap(
						code -> code,
						code -> patientService.getPatientNameByCode(code)
				));
		model.addAttribute("patientNameMap", patientNameMap);

		return "financeEmployee/create-transaction-customer";
	}

	// --- Submit form thêm mới ---
	@PostMapping("/transactions-customer/create")
	public String createTransaction(
			@Valid @ModelAttribute("transactionRequest") CreateTransactionCustomerRequest request,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (result.hasErrors()) {
			// Nếu lỗi, trả lại form với danh sách vaccines và patientCodes
			model.addAttribute("vaccines", invoiceService.getActiveVaccines());
			model.addAttribute("patientCodes", patientService.getAllPatientCodes());
			return "financeEmployee/create-transaction-customer";
		}

		invoiceService.createTransaction(request);
		redirectAttributes.addFlashAttribute("success", "Thêm giao dịch khách hàng thành công!");
		return "redirect:/finance/transactions-customer";
	}

	// 🆕 Xử lý xóa mềm hóa đơn
	@PostMapping("/transactions-customer/delete")
	public String deleteTransaction(@RequestParam("invoiceId") String invoiceId,
									RedirectAttributes redirectAttributes) {
		invoiceService.deleteTransaction(invoiceId);
		redirectAttributes.addFlashAttribute("success", "Xóa hóa đơn thành công!");
		return "redirect:/finance/transactions-customer";
	}
	
	@GetMapping("/transactions-customer/edit/{invoiceId}")
	public String showEditForm(@PathVariable String invoiceId, Model model) {
	    TransactionCustomerResponse dto = invoiceService.getTransactionById(invoiceId);

	    // ✅ Format LocalDateTime sang dạng yyyy-MM-dd'T'HH:mm cho input datetime-local
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    String formattedDateTime = dto.getDateTime() != null ? dto.getDateTime().format(formatter) : "";

	    model.addAttribute("transactionRequest", dto);
	    model.addAttribute("formattedDateTime", formattedDateTime);

	    model.addAttribute("vaccines", invoiceService.getActiveVaccines());

	    List<String> patientCodes = patientService.getAllPatientCodes();
	    model.addAttribute("patientCodes", patientCodes);

	    Map<String, String> patientNameMap = patientCodes.stream()
	            .collect(Collectors.toMap(
	                    code -> code,
	                    code -> patientService.getPatientNameByCode(code)
	            ));
	    model.addAttribute("patientNameMap", patientNameMap);

	    return "financeEmployee/edit-transaction-customer";
	}


	@PostMapping("/transactions-customer/update")
	public String updateTransaction(
	        @Valid @ModelAttribute("transactionRequest") UpdateTransactionCustomerRequest request,
	        BindingResult result,
	        RedirectAttributes redirectAttributes,
	        Model model) {

	    if (result.hasErrors()) {
	        model.addAttribute("vaccines", invoiceService.getActiveVaccines());
	        model.addAttribute("patientCodes", patientService.getAllPatientCodes());
	        return "financeEmployee/edit-transaction-customer";
	    }

	    invoiceService.updateTransaction(request);
	    redirectAttributes.addFlashAttribute("success", "Cập nhật giao dịch khách hàng thành công!");
	    return "redirect:/finance/transactions-customer";
	}

	// 🆕 Quản lý giao dịch nhà cung cấp
	@GetMapping("/transactions-supplier")
	public String showTransactionSupplier(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Model model) {

		Page<TransactionSupplierResponse> transactions = invoiceSupplierService.getAllSupplierTransactions(page, size);

		model.addAttribute("transactions", transactions.getContent()); // danh sách hóa đơn NCC
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", transactions.getTotalPages());
		model.addAttribute("pageSize", size);

		return "financeEmployee/transaction-supplier"; // view Thymeleaf
	}

	// --- Hiển thị form thêm mới ---
	@GetMapping("/transactions-supplier/new")
	public String showCreateSupplierForm(Model model) {
		model.addAttribute("transactionRequest", new CreateTransactionSupplierRequest());
		model.addAttribute("vaccines", invoiceService.getActiveVaccines());
		return "financeEmployee/create-transaction-supplier";
	}

	// --- Submit form thêm mới ---
	@PostMapping("/transactions-supplier/create")
	public String createSupplierTransaction(
			@Valid @ModelAttribute("transactionRequest") CreateTransactionSupplierRequest request,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("vaccines", invoiceService.getActiveVaccines());
			return "financeEmployee/create-transaction-supplier";
		}

		invoiceSupplierService.createSupplierTransaction(request);
		redirectAttributes.addFlashAttribute("success", "Thêm giao dịch nhà cung cấp thành công!");
		return "redirect:/finance/transactions-supplier";
	}

	// --- Xóa mềm NCC transaction ---
	@PostMapping("/transactions-supplier/delete")
	public String deleteSupplierTransaction(@RequestParam("invoiceId") String invoiceId,
											RedirectAttributes redirectAttributes) {
		invoiceSupplierService.deleteSupplierTransaction(invoiceId);
		redirectAttributes.addFlashAttribute("success", "Xóa giao dịch NCC thành công!");
		return "redirect:/finance/transactions-supplier";
	}

	// --- Hiển thị form sửa ---
	@GetMapping("/transactions-supplier/edit/{invoiceId}")
	public String showEditSupplierForm(@PathVariable String invoiceId, Model model) {
		TransactionSupplierResponse dto = invoiceSupplierService.getSupplierTransactionById(invoiceId);

		// format datetime cho input type="datetime-local"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		String formattedDateTime = dto.getDateTimeSupplier() != null ? dto.getDateTimeSupplier().format(formatter) : "";

		model.addAttribute("transactionRequest", dto);
		model.addAttribute("formattedDateTime", formattedDateTime);

		return "financeEmployee/edit-transaction-supplier";
	}

	// --- Submit update ---
	@PostMapping("/transactions-supplier/update")
	public String updateSupplierTransaction(
			@Valid @ModelAttribute("transactionRequest") UpdateTransactionSupplierRequest request,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (result.hasErrors()) {
			return "financeEmployee/edit-transaction-supplier";
		}

		invoiceSupplierService.updateSupplierTransaction(request);
		redirectAttributes.addFlashAttribute("success", "Cập nhật giao dịch nhà cung cấp thành công!");
		return "redirect:/finance/transactions-supplier";
	}

}
