package com.viettridao.vaccination.dto.response.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {
    private String maLoCode;           // Mã lô
    private String tenVacXin;          // Tên vắc xin
    private String loaiVacXin;         // Loại vắc xin
    private LocalDate ngayNhap;        // Ngày nhập
    private String soGiayPhep;         // Số giấy phép
    private String nuocSanXuat;        // Nước sản xuất
    private String hamLuong;           // Hàm lượng
    private Integer soLuong;           // Số lượng
    private LocalDate hanSuDung;       // Hạn sử dụng
    private String dieuKienBaoQuan;    // Điều kiện bảo quản
    private String doiTuongTiem;       // Độ tuổi tiêm chủng
    private String tinhTrangVacXin;    // Tình trạng vắc xin
}