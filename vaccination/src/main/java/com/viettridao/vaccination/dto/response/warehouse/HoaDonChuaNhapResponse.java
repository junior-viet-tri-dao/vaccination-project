package com.viettridao.vaccination.dto.response.warehouse;

import lombok.Data;

@Data
public class HoaDonChuaNhapResponse {
    private String soHoaDon;
    private String maLoCode;
    private String tenVacXin;
    private String loaiVacXin;
    private String ngayNhap;   // hoặc LocalDate nếu muốn, khi truyền sang FE thì format thành String
    private Integer soLuong;
    private Integer donGia;
}