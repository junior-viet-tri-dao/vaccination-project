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

    @NotNull(message = "Vắc xin không được để trống")
    private String vaccineId;

    @NotNull(message = "Nhân viên phụ trách không được để trống")
    private String nhanVienId;

    @NotBlank(message = "Địa điểm tiêm không được để trống")
    private String diaDiem;

    @NotNull(message = "Thời gian tiêm không được để trống")
    private LocalDateTime thoiGianTiem;

    @NotBlank(message = "Kết quả không được để trống")
    @Size(min = 5, max = 200, message = "Kết quả phải từ {min} đến {max} ký tự")
    private String ketQua;
}