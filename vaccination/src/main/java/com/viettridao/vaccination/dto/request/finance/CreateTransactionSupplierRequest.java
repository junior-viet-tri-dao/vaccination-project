package com.viettridao.vaccination.dto.request.finance;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionSupplierRequest {

    // Ngày giao dịch với nhà cung cấp (bắt buộc, không được ở tương lai)
    @NotNull(message = "Ngày giao dịch không được để trống")
    @PastOrPresent(message = "Ngày giao dịch không thể ở tương lai")
    private LocalDateTime dateTimeSupplier;

    // Mã hóa đơn (bắt buộc, tối đa 10 ký tự, chỉ cho phép ký tự an toàn)
    @NotBlank(message = "Mã hóa đơn không được để trống")
    @Size(max = 10, message = "Mã hóa đơn không quá 10 ký tự")
    private String invoiceCode;

    // Mã vắc xin (bắt buộc, tối đa 10 ký tự)
    @NotBlank(message = "Mã vắc xin không được để trống")
    @Size(max = 10, message = "Mã vắc xin không quá 10 ký tự")
    private String vaccineCode;

    // Số lượng nhập từ nhà cung cấp (bắt buộc, >=1, <= 10,000)
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng tối thiểu là 1")
    @Max(value = 10000, message = "Số lượng tối đa là 10,000")
    private Integer quantity;

    // Tên nhà cung cấp (bắt buộc, tối đa 100 ký tự, không chứa ký tự đặc biệt nguy hiểm)
    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    @Size(max = 20, message = "Tên nhà cung cấp không quá 20 ký tự")
    private String supplierName;

    // Giá nhà cung cấp (bắt buộc, >=1, <= 1,000,000,000)
    @NotNull(message = "Giá không được để trống")
    @Min(value = 1, message = "Giá tối thiểu là 1")
    @Max(value = 1000000000, message = "Giá tối đa là 1,000,000,000")
    private Integer price;
}
