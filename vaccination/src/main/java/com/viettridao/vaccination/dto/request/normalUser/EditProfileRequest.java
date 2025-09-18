package com.viettridao.vaccination.dto.request.normalUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditProfileRequest {
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 20, message = "Tên phải từ 2 đến 20 ký tự")
    private String ten;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate ngaySinh;

    @NotNull(message = "Giới tính không được để trống")
    private GioiTinh gioiTinh;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min = 5, max = 50, message = "Địa chỉ phải từ 5 đến 50 ký tự")
    private String diaChi;

    public enum GioiTinh {
        NAM, NU
    }
}