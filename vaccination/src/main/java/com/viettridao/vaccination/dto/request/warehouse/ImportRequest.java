package com.viettridao.vaccination.dto.request.warehouse;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportRequest {
    @NotBlank(message = "Mã lô không được để trống")
    @Size(max = 10, message = "Mã lô tối đa 10 ký tự")
    private String maLoCode;           // Mã lô

    @NotBlank(message = "Tên vắc xin không được để trống")
    @Size(max = 10, message = "Tên vắc xin tối đa 10 ký tự")
    private String tenVacXin;          // Tên vắc xin

    @NotBlank(message = "Loại vắc xin không được để trống")
    @Size(max = 10, message = "Loại vắc xin tối đa 10 ký tự")
    private String loaiVacXin;         // Loại vắc xin

    @NotNull(message = "Ngày nhập không được để trống")
    @PastOrPresent(message = "Ngày nhập không được là tương lai")
    private LocalDate ngayNhap;        // Ngày nhập

    @NotBlank(message = "Số giấy phép không được để trống")
    @Size(max = 20, message = "Số giấy phép tối đa 20 ký tự")
    private String soGiayPhep;         // Số giấy phép

    @NotBlank(message = "Nước sản xuất không được để trống")
    @Size(max = 10, message = "Nước sản xuất tối đa 10 ký tự")
    private String nuocSanXuat;        // Nước sản xuất

    @NotBlank(message = "Hàm lượng không được để trống")
    @Size(max = 30, message = "Hàm lượng tối đa 30 ký tự")
    private String hamLuong;           // Hàm lượng

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải >= 1")
    @Max(value = 100000, message = "Số lượng tối đa 100000")
    private Integer soLuong;           // Số lượng

    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 1, message = "Đơn giá phải >= 1")
    @Max(value = 1000000000, message = "Đơn giá tối đa 1,000,000,000")
    private Integer donGia;            // Đơn giá

    @NotNull(message = "Hạn sử dụng không được để trống")
    @Future(message = "Hạn sử dụng phải là ngày trong tương lai")
    private LocalDate hanSuDung;       // Hạn sử dụng

    @NotBlank(message = "Điều kiện bảo quản không được để trống")
    @Size(max = 10, message = "Điều kiện bảo quản tối đa 10 ký tự")
    private String dieuKienBaoQuan;    // Điều kiện bảo quản

    @NotBlank(message = "Đối tượng tiêm chủng không được để trống")
    @Size(max = 20, message = "Đối tượng tiêm chủng tối đa 20 ký tự")
    private String doiTuongTiem;       // Độ tuổi tiêm chủng
}