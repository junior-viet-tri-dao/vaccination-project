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
public class PhanHoiSauTiemResponse {
    private String id;               // Mã phản hồi/ID báo cáo phản ứng
    private String tenVacXin;
    private LocalDateTime thoiGian;
    private String diaDiem;
    private String nhanVienPhuTrach;
    private String moTa;             // Nội dung phản hồi/triệu chứng
    private String trangThai;        // Trạng thái xử lý phản hồi (DA_PHAN_HOI, DA_XU_LY, ...)
    private String ketQuaTiemId;
    private String trangThaiPhanHoi;
}