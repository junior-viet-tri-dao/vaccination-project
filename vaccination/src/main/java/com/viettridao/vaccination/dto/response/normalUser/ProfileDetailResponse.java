package com.viettridao.vaccination.dto.response.normalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDetailResponse {
    // Thông tin cá nhân
    private String id;
    private String ten;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;

    // Danh sách lịch sử tiêm
    private List<LichSuTiemResponse> lichSuTiem;
}