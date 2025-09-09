package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionSupplierRequest;
import com.viettridao.vaccination.dto.request.finance.UpdateTransactionSupplierRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionSupplierResponse;
import com.viettridao.vaccination.mapper.TransactionSupplierMapper;
import com.viettridao.vaccination.model.InvoiceEntity;
import com.viettridao.vaccination.model.SupplierEntity;
import com.viettridao.vaccination.model.VaccineEntity;
import com.viettridao.vaccination.repository.InvoiceRepository;
import com.viettridao.vaccination.repository.SupplierRepository;
import com.viettridao.vaccination.repository.VaccineRepository;
import com.viettridao.vaccination.service.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final InvoiceRepository invoiceRepository;
    private final SupplierRepository supplierRepository;
    private final TransactionSupplierMapper supplierMapper;
    private final VaccineRepository vaccineRepository;

    @Override
    public Page<TransactionSupplierResponse> getAllSupplierTransactions(int page, int size) {
        Page<InvoiceEntity> invoices = invoiceRepository.findByIsDeletedFalse(PageRequest.of(page, size));

        // Map entity -> dto và set thủ công supplierName + vaccineCode
        return invoices.map(entity -> {
            TransactionSupplierResponse dto = supplierMapper.toDto(entity);
            if (entity.getSupplier() != null) {
                dto.setSupplierName(entity.getSupplier().getSupplierName());
            }
            if (entity.getVaccine() != null) {
                dto.setVaccineCode(entity.getVaccine().getVaccineCode());
            }
            return dto;
        });
    }

    @Override
    @Transactional
    public TransactionSupplierResponse createSupplierTransaction(CreateTransactionSupplierRequest request) {
        // 1. Tìm hoặc tạo Supplier
        SupplierEntity supplier = supplierRepository.findBySupplierNameAndIsDeletedFalse(request.getSupplierName())
                .orElseGet(() -> supplierRepository.save(
                        SupplierEntity.builder()
                                .supplierName(request.getSupplierName())
                                .isDeleted(false)
                                .build()
                ));

        // 2. Map request -> entity
        InvoiceEntity entity = supplierMapper.fromCreateRequest(request);
        entity.setSupplier(supplier);

        // 👉 2b. Tìm Vaccine theo vaccineCode
        if (request.getVaccineCode() != null) {
            VaccineEntity vaccine = vaccineRepository.findByVaccineCodeAndIsDeletedFalse(request.getVaccineCode())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vắc xin: " + request.getVaccineCode()));
            entity.setVaccine(vaccine);
        }

        // 3. Tính totalAmount
        if (entity.getSupplierPrice() != null && entity.getQuantityInvoice() != null) {
            entity.setTotalAmount(entity.getSupplierPrice() * entity.getQuantityInvoice());
        }

        // 4. Lưu DB
        InvoiceEntity saved = invoiceRepository.save(entity);

        // 5. Trả về DTO
        TransactionSupplierResponse dto = supplierMapper.toDto(saved);
        dto.setSupplierName(supplier.getSupplierName());
        if (saved.getVaccine() != null) {
            dto.setVaccineCode(saved.getVaccine().getVaccineCode());
        }
        return dto;
    }

    @Override
    @Transactional
    public TransactionSupplierResponse updateSupplierTransaction(UpdateTransactionSupplierRequest request) {
        InvoiceEntity entity = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        // Map request -> entity
        supplierMapper.updateEntityFromRequest(request, entity);

        if (request.getVaccineCode() != null) {
            VaccineEntity vaccine = vaccineRepository.findByVaccineCodeAndIsDeletedFalse(request.getVaccineCode())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vắc xin: " + request.getVaccineCode()));
            entity.setVaccine(vaccine);
        }

        // Tìm hoặc tạo Supplier
        SupplierEntity supplier = supplierRepository.findBySupplierNameAndIsDeletedFalse(request.getSupplierName())
                .orElseGet(() -> supplierRepository.save(
                        SupplierEntity.builder()
                                .supplierName(request.getSupplierName())
                                .isDeleted(false)
                                .build()
                ));
        entity.setSupplier(supplier);

        // 👉 Tính lại totalAmount
        if (entity.getSupplierPrice() != null && entity.getQuantityInvoice() != null) {
            entity.setTotalAmount(entity.getSupplierPrice() * entity.getQuantityInvoice());
        }

        // Lưu lại
        InvoiceEntity updated = invoiceRepository.save(entity);

        // Map sang DTO
        TransactionSupplierResponse dto = supplierMapper.toDto(updated);
        dto.setSupplierName(supplier.getSupplierName());
        if (updated.getVaccine() != null) {
            dto.setVaccineCode(updated.getVaccine().getVaccineCode());
        }
        return dto;
    }

    @Override
    @Transactional
    public void deleteSupplierTransaction(String invoiceId) {
        InvoiceEntity entity = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        entity.setIsDeleted(true);
        invoiceRepository.save(entity);
    }

    @Override
    public TransactionSupplierResponse getSupplierTransactionById(String invoiceId) {
        InvoiceEntity entity = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        // Map sang DTO + set thủ công
        TransactionSupplierResponse dto = supplierMapper.toDto(entity);
        if (entity.getSupplier() != null) {
            dto.setSupplierName(entity.getSupplier().getSupplierName());
        }
        if (entity.getVaccine() != null) {
            dto.setVaccineCode(entity.getVaccine().getVaccineCode());
        }
        return dto;
    }
}