package com.viettridao.vaccination.dto.response.normalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuTiemResponse {
    private Integer stt;
    private LocalDateTime thoiGian;
    private String diaDiem;
    private String tenVacXin;
    private String loaiVacXin;
    private String lieuLuong;
    private String nguoiTiem;
    private String ketQua;
    private String ghiChu;
}