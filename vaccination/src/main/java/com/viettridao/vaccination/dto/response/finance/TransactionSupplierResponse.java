package com.viettridao.vaccination.dto.response.finance;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionSupplierResponse {

    private String invoiceId;        // ID hóa đơn (dùng cho sửa, xóa)
    private LocalDateTime dateTimeSupplier; // Ngày giao dịch với NCC
    private String invoiceCode;      // Mã hóa đơn
    private String vaccineCode;      // Mã vắc xin
    private Integer quantity;        // Số lượng
    private String supplierName;     // Nhà cung cấp
    private Integer price;           // Giá NCC
}
