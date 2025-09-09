package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionSupplierRequest;
import com.viettridao.vaccination.dto.request.finance.UpdateTransactionSupplierRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionSupplierResponse;
import org.springframework.data.domain.Page;

public interface SupplierService {

    // Lấy danh sách giao dịch với NCC (có phân trang)
    Page<TransactionSupplierResponse> getAllSupplierTransactions(int page, int size);

    // Thêm mới giao dịch với NCC
    TransactionSupplierResponse createSupplierTransaction(CreateTransactionSupplierRequest request);

    // Cập nhật giao dịch với NCC
    TransactionSupplierResponse updateSupplierTransaction(UpdateTransactionSupplierRequest request);

    // Xóa mềm giao dịch với NCC
    void deleteSupplierTransaction(String invoiceId);

    // Xem chi tiết giao dịch
    TransactionSupplierResponse getSupplierTransactionById(String invoiceId);
}
