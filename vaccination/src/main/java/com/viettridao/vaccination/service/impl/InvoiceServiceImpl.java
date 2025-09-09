package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.request.finance.UpdateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionCustomerResponse;
import com.viettridao.vaccination.mapper.TransactionCustomerMapper;
import com.viettridao.vaccination.model.InvoiceEntity;
import com.viettridao.vaccination.model.InvoiceExtraEntity;
import com.viettridao.vaccination.model.VaccineEntity;
import com.viettridao.vaccination.repository.InvoiceExtraRepository;
import com.viettridao.vaccination.repository.InvoiceRepository;
import com.viettridao.vaccination.repository.VaccineRepository;
import com.viettridao.vaccination.service.InvoiceService;
import com.viettridao.vaccination.service.PatientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

	private final InvoiceRepository invoiceRepository;
	private final TransactionCustomerMapper transactionCustomerMapper;
	private final VaccineRepository vaccineRepository;
	private final PatientService patientService;
	private final InvoiceExtraRepository invoiceExtraRepository;


	@Override
	public Page<TransactionCustomerResponse> getAllTransactions(int page, int size) {
	    Page<InvoiceEntity> invoices =
	            invoiceRepository.findAllSortedByDateTime(PageRequest.of(page, size));

	    return invoices.map(invoice -> {
	        TransactionCustomerResponse dto = transactionCustomerMapper.toDto(invoice);

	        // Lấy dữ liệu từ InvoiceExtra (dành cho dữ liệu mới)
	        invoiceExtraRepository.findByInvoice_InvoiceId(invoice.getInvoiceId())
	            .ifPresentOrElse(
	                extra -> {
	                    dto.setCustomerName(extra.getCustomerName());
	                    dto.setVaccineCode(extra.getVaccineCode());
	                    dto.setDateTime(extra.getDateTime());
	                },
	                () -> {
	                    // fallback từ MedicalRecordEntity nếu tồn tại
	                    if (invoice.getMedicalRecordEntity() != null) {
	                        dto.setCustomerName(
	                            invoice.getMedicalRecordEntity().getRegistrationDetail() != null
	                                ? invoice.getMedicalRecordEntity().getRegistrationDetail().getPatient().getPatientName()
	                                : "Không rõ"
	                        );
	                        dto.setVaccineCode(
	                            invoice.getMedicalRecordEntity().getRegistrationDetail() != null
	                                ? invoice.getMedicalRecordEntity().getRegistrationDetail().getVaccine().getVaccineCode()
	                                : "Không rõ"
	                        );
	                    } else {
	                        dto.setCustomerName("Không rõ");
	                        dto.setVaccineCode("Không rõ");
	                    }
	                }
	            );

	        return dto;
	    });
	}

	@Override
	public TransactionCustomerResponse createTransaction(CreateTransactionCustomerRequest request) {

		// 1️⃣ Tạo InvoiceEntity như bình thường
		InvoiceEntity invoice = transactionCustomerMapper.fromCreateRequest(request);
		invoice.setCustomerPrice(request.getPrice() != null ? request.getPrice() : 0);
		invoice.setQuantityInvoice(request.getQuantity() != null ? request.getQuantity() : 0);
		invoice.setTotalAmount(invoice.getCustomerPrice() * invoice.getQuantityInvoice());
		invoice.setIsDeleted(false);
		invoice.setMedicalRecordEntity(null);

		// 2️⃣ Lưu InvoiceEntity
		InvoiceEntity savedInvoice = invoiceRepository.save(invoice);

		// 3️⃣ Lưu dữ liệu 3 trường vào bảng phụ InvoiceExtra
		InvoiceExtraEntity extra = new InvoiceExtraEntity();
		extra.setInvoice(savedInvoice);
		extra.setCustomerName(patientService.getPatientNameByCode(request.getPatientCode()));
		extra.setVaccineCode(request.getVaccineCode());
		extra.setDateTime(request.getDateTime());

		invoiceExtraRepository.save(extra);

		// 4️⃣ Map DTO
		TransactionCustomerResponse dto = transactionCustomerMapper.toDto(savedInvoice);

		// 5️⃣ Gán dữ liệu từ InvoiceExtra để UI hiển thị đúng
		dto.setCustomerName(extra.getCustomerName());
		dto.setVaccineCode(extra.getVaccineCode());
		dto.setDateTime(extra.getDateTime());

		return dto;
	}
	
	@Override
	public TransactionCustomerResponse updateTransaction(UpdateTransactionCustomerRequest request) {
	    // 1️⃣ Lấy InvoiceEntity hiện tại
	    InvoiceEntity invoice = invoiceRepository.findById(request.getInvoiceId())
	            .orElseThrow(() -> new RuntimeException("Invoice not found: " + request.getInvoiceId()));

	    // 2️⃣ Cập nhật các trường cơ bản
	    invoice.setCustomerPrice(request.getPrice());
	    invoice.setQuantityInvoice(request.getQuantity());
	    invoice.setTotalAmount(request.getPrice() * request.getQuantity());
	    invoiceRepository.save(invoice);

	    // 3️⃣ Cập nhật hoặc tạo InvoiceExtra
	    InvoiceExtraEntity extra = invoiceExtraRepository.findByInvoice_InvoiceId(invoice.getInvoiceId())
	            .orElseGet(() -> {
	                InvoiceExtraEntity e = new InvoiceExtraEntity();
	                e.setInvoice(invoice);
	                return e;
	            });

	    extra.setCustomerName(request.getCustomerName());
	    extra.setVaccineCode(request.getVaccineCode());
	    extra.setDateTime(request.getDateTime());
	    invoiceExtraRepository.save(extra);

	    // 4️⃣ Map sang DTO trả về
	    TransactionCustomerResponse dto = transactionCustomerMapper.toDto(invoice);
	    dto.setCustomerName(extra.getCustomerName());
	    dto.setVaccineCode(extra.getVaccineCode());
	    dto.setDateTime(extra.getDateTime());

	    return dto;
	}

	
	@Override
	public TransactionCustomerResponse getTransactionById(String invoiceId) {
	    // 1️⃣ Lấy InvoiceEntity
	    InvoiceEntity invoice = invoiceRepository.findById(invoiceId)
	            .orElseThrow(() -> new RuntimeException("Invoice not found: " + invoiceId));

	    // 2️⃣ Map sang DTO
	    TransactionCustomerResponse dto = transactionCustomerMapper.toDto(invoice);

	    // 3️⃣ Nếu có InvoiceExtra, lấy dữ liệu bổ sung
	    invoiceExtraRepository.findByInvoice_InvoiceId(invoiceId).ifPresent(extra -> {
	        dto.setCustomerName(extra.getCustomerName());
	        dto.setVaccineCode(extra.getVaccineCode());
	        dto.setDateTime(extra.getDateTime());
	    });

	    return dto;
	}


	@Override
	public List<VaccineEntity> getActiveVaccines() {
		return vaccineRepository.findByIsDeletedFalse();
	}

	@Override
	public void deleteTransaction(String invoiceId) {
		InvoiceEntity invoice = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new RuntimeException("Invoice not found: " + invoiceId));
		invoice.setIsDeleted(true); // Xóa mềm
		invoiceRepository.save(invoice);
	}

}
