package com.viettridao.vaccination.dto.response.normalUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO cho hiển thị lịch tiêm chủng của cá nhân user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineScheduleResponse {
    private int stt;
    private LocalTime thoiGian;      // Thời gian tiêm (ví dụ: 07:00)
    private LocalDate ngayTiem;      // Ngày tiêm
    private String diaDiem;          // Địa điểm
    private String tenVacXin;        // Tên vắc xin
    private String loaiVacXin;       // Loại vắc xin
    private int soLuong;             // Số lượng (liều)
    private String doiTuong;         // Đối tượng (ví dụ: "<2 tuổi", "Người lớn", ...)
    private String ghiChu;           // Ghi chú (giá 1 mũi/đối tượng, miễn phí, v.v...)
}