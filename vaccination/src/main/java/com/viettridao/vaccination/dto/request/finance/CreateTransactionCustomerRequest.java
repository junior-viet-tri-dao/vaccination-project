package com.viettridao.vaccination.dto.request.finance;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionCustomerRequest {

    // Ngày giao dịch (không được null, không được trong tương lai)
    @NotNull(message = "Ngày giao dịch không được để trống")
    @PastOrPresent(message = "Ngày giao dịch không thể ở tương lai")
    private LocalDateTime dateTime;

    // Mã bệnh nhân (không rỗng, độ dài tối đa 10 ký tự)
    @NotBlank(message = "Mã bệnh nhân không được để trống")
    @Size(max = 10, message = "Mã bệnh nhân không quá 10 ký tự")
    private String patientCode;

    // Mã hóa đơn
    @NotBlank(message = "Mã hóa đơn không được để trống")
    @Size(max = 10, message = "Mã hóa đơn không quá 10 ký tự")
    private String invoiceCode;

    // Mã vắc xin
    @NotBlank(message = "Mã vắc xin không được để trống")
    private String vaccineCode;

    // Tên khách hàng
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String customerName;

    // Số lượng (>= 1 và <= 1000)
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng tối thiểu là 1")
    @Max(value = 100, message = "Số lượng tối đa là 100")
    private Integer quantity;

    // Giá (>= 1 và <= 100 triệu)
    @NotNull(message = "Giá không được để trống")
    @Min(value = 1, message = "Giá tối thiểu là 1")
    @Max(value = 100000000, message = "Giá tối đa là 100,000,000")
    private Integer price;
}
