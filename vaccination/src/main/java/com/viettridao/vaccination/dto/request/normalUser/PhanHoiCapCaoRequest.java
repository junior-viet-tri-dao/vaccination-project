package com.viettridao.vaccination.dto.request.normalUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanHoiCapCaoRequest {

    @NotNull(message = "Loại phản hồi không được để trống")
    private LoaiPhanHoi loaiPhanHoi;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(min = 10, max = 2000, message = "Nội dung phải từ {min} đến {max} ký tự")
    private String noiDung;

    public enum LoaiPhanHoi {
        PHAN_NAN, DONG_VIEN, GOP_Y, CAU_HOI
    }
}