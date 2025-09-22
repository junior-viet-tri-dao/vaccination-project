package com.viettridao.vaccination.dto.response.employee;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KetQuaTiemResponse {
    private String maKq;
    private String tenBenhNhan;
    private String tenVacXin;
    private LocalDateTime ngayTiem;
    private String nguoiThucHien;
    private String tinhTrang;
    private String phanUngSauTiem;
    private String diaDiem;
    private String ghiChu;
}