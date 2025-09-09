package com.viettridao.vaccination.dto.response.finance;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCustomerResponse {

    private int index;              // STT hiển thị

    private LocalDateTime dateTime;         // Ngày giờ giao dịch

    private String invoiceId;       // Khóa chính hóa đơn

    private String invoiceCode;     // Mã hóa đơn hiển thị

    private String vaccineCode;     // Mã vắc xin

    private Integer quantity;       // Số lượng

    private String customerName;    // Tên khách hàng

    private Integer price;          // Giá

    private String patientCode;   // Mã bệnh nhân
    
}