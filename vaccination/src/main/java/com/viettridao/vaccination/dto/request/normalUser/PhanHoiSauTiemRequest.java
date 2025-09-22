package com.viettridao.vaccination.dto.request.normalUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanHoiSauTiemRequest {
    @NotBlank(message = "Phản hồi không được để trống")
    @Size(min = 5, max = 200, message = "Phản hồi phải từ {min} đến {max} ký tự")
    private String moTa; // Nội dung phản hồi/triệu chứng

    private String ketQuaTiemId;  // Liên kết đến kết quả tiêm
}