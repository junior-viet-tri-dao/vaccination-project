package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionCustomerResponse;
import com.viettridao.vaccination.model.VaccineEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InvoiceService {
    Page<TransactionCustomerResponse> getAllTransactions(int page, int size);

    // 🆕 Thêm phương thức xóa mềm hóa đơn
    void deleteTransaction(String invoiceId);

    // 🆕 Thêm hóa đơn
    TransactionCustomerResponse createTransaction(CreateTransactionCustomerRequest request);

    List<VaccineEntity> getActiveVaccines();
}