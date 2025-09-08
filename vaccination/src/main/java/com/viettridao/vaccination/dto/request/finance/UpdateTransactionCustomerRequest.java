package com.viettridao.vaccination.dto.request.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTransactionCustomerRequest {

    private String invoiceId;       // Xác định hóa đơn cần update
    private String invoiceCode;     // Cho phép update/hiển thị lại mã hóa đơn

    private LocalDateTime dateTime;         // Ngày giao dịch

    private String vaccineCode;     // Mã vắc xin

    private Integer quantity;       // Số lượng

    private String customerName;    // Tên khách hàng

    private Integer price;          // Giá

    private String patientCode;   // Mã bệnh nhân
}
