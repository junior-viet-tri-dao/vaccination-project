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
public class ImportResponse {
	private String soHoaDon;           // Mã số hóa đơn

	private String maLoCode;           // Mã lô
	private String tenVacXin;          // Tên vắc xin
	private String loaiVacXin;         // Loại vắc xin

	private LocalDate ngayNhap;        // Ngày nhập
	private String hamLuong;           // Hàm lượng
	private Integer soLuong;           // Số lượng
	private Integer donGia;            // Đơn giá

	private LocalDate ngaySanXuat;     // Ngày sản xuất
	private String donVi;              // Đơn vị

	private LocalDate hanSuDung;       // Hạn sử dụng
	private String soGiayPhep;         // Số giấy phép
	private String dieuKienBaoQuan;    // Điều kiện bảo quản
	private String doiTuongTiem;       // Đối tượng tiêm chủng
	private String nuocSanXuat;        // Nước sản xuất
}