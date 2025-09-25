package com.viettridao.vaccination.dto.request.adminPanel;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class TaiKhoanCreateRequest {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4, max = 20, message = "Tên đăng nhập phải từ 4-20 ký tự")
    private String tenDangNhap;      // Username

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 20, message = "Mật khẩu phải từ 6-20 ký tự")
    private String matKhauRaw;       // Raw password (sẽ được mã hóa bằng Bcrypt)

    @NotEmpty(message = "Vai trò không được để trống")
    private List<@NotBlank(message = "ID vai trò không được để trống") String> vaiTroIds; // Danh sách id vai trò

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 20, message = "Họ tên phải từ 2 đến 20 ký tự")
    private String hoTen;            // Họ tên

    @NotBlank(message = "Số CMND không được để trống")
    @Pattern(regexp = "\\d{9,12}", message = "CMND phải từ 9-12 số")
    private String soCmnd;           // CMND

    @NotBlank(message = "Nơi ở không được để trống")
    @Size(min = 5, max = 30, message = "Nơi ở phải từ 5 đến 30 ký tự")
    private String diaChi;

    @Size(max = 255, message = "Mô tả tối đa 255 ký tự")
    private String description;      // Mô tả/ghi chú (nếu muốn lưu)
}