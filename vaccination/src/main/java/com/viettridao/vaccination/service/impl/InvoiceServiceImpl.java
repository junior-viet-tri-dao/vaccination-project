package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionCustomerResponse;
import com.viettridao.vaccination.mapper.TransactionCustomerMapper;
import com.viettridao.vaccination.model.InvoiceEntity;
import com.viettridao.vaccination.model.MedicalRecordEntity;
import com.viettridao.vaccination.model.PatientEntity;
import com.viettridao.vaccination.model.VaccineEntity;
import com.viettridao.vaccination.repository.InvoiceRepository;
import com.viettridao.vaccination.repository.MedicalRecordRepository;
import com.viettridao.vaccination.repository.VaccineRepository;
import com.viettridao.vaccination.service.InvoiceService;
import com.viettridao.vaccination.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final TransactionCustomerMapper transactionCustomerMapper;
    private final VaccineRepository vaccineRepository;
    private final PatientService patientService;

    @Override
    public Page<TransactionCustomerResponse> getAllTransactions(int page, int size) {
        Page<InvoiceEntity> invoices = invoiceRepository.findByIsDeletedFalse(PageRequest.of(page, size));
        return invoices.map(transactionCustomerMapper::toDto);
    }

    @Override
    public TransactionCustomerResponse createTransaction(CreateTransactionCustomerRequest request) {
        // 1️⃣ Map request -> entity
        InvoiceEntity invoice = transactionCustomerMapper.fromCreateRequest(request);

        // 2️⃣ Set giá và số lượng, tránh null
        invoice.setCustomerPrice(request.getPrice() != null ? request.getPrice() : 0);
        invoice.setQuantityInvoice(request.getQuantity() != null ? request.getQuantity() : 0);

        // 3️⃣ Tính tổng tiền
        invoice.setTotalAmount(invoice.getCustomerPrice() * invoice.getQuantityInvoice());

        // 4️⃣ Đánh dấu không xoá (soft delete)
        invoice.setIsDeleted(false);

        // 5️⃣ Vì chưa có method lấy MedicalRecordEntity, tạm set null
        invoice.setMedicalRecordEntity(null);

        // 6️⃣ Lưu entity vào DB
        InvoiceEntity saved = invoiceRepository.save(invoice);

        // 7️⃣ Trả về DTO
        TransactionCustomerResponse dto = transactionCustomerMapper.toDto(saved);

        // 8️⃣ Map thêm customerName thủ công từ patientService
        String patientName = patientService.getPatientNameByCode(request.getPatientCode());
        dto.setCustomerName(patientName);

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

