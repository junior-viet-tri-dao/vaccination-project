package com.viettridao.vaccination.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO dùng để trả về thông tin dịch bệnh
 * Bao gồm cả số thứ tự (stt) để hiển thị lên bảng.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DichBenhResponse {

    private Integer stt; // Số thứ tự trong bảng

    private LocalDate thoiDiemKhaoSat;

    private String diaChi;

    private String tenBenh;

    private Integer soNguoiBiNhiem;

    private String duongLayNhiem;

    private String tacHaiSucKhoe;

    private String loaiVacXinPhongBenh;

    private String ghiChu;
}