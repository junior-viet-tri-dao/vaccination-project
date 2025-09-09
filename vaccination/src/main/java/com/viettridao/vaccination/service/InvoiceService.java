package com.viettridao.vaccination.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.request.finance.UpdateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionCustomerResponse;
import com.viettridao.vaccination.model.VaccineEntity;

public interface InvoiceService {
    Page<TransactionCustomerResponse> getAllTransactions(int page, int size);

    // 🆕 Thêm phương thức xóa mềm hóa đơn
    void deleteTransaction(String invoiceId);

    // 🆕 Thêm hóa đơn
    TransactionCustomerResponse createTransaction(CreateTransactionCustomerRequest request);

    List<VaccineEntity> getActiveVaccines();
    
    TransactionCustomerResponse updateTransaction(UpdateTransactionCustomerRequest request); // <-- thêm dòng này

    TransactionCustomerResponse getTransactionById(String invoiceId); // nếu cần

}