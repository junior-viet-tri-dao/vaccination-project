package com.viettridao.vaccination.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO phản hồi dành cho màn hình danh sách phản hồi cấp cao (admin).
 * Hiện: Loại phản hồi, Ngày, Nội dung, Tên bệnh nhân, Trạng thái.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanHoiAdminResponse {
    private String id;               // Mã phản hồi (ma_ph)
    private String loaiPhanHoi;      // Loại phản hồi (enum.toString)
    private LocalDateTime ngayTao;   // Ngày tạo
    private String noiDung;          // Nội dung phản hồi
    private String tenBenhNhan;      // Tên bệnh nhân
    private String trangThai;        // Trạng thái (enum.toString)
}