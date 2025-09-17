package com.viettridao.vaccination.dto.request.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExportRequest {
    @NotBlank(message = "Bạn phải chọn lô vắc xin cần xuất kho.")
    private String maLoCode;           // Mã lô

    @Min(value = 1, message = "Số lượng phải >= 1.")
    private int quantity;

    private int maxQuantity;
}