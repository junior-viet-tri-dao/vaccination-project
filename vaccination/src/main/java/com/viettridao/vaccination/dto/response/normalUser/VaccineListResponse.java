package com.viettridao.vaccination.dto.response.normalUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineListResponse {
    private String maVacXin;       // Mã vắc xin
    private String tenVacXin;      // Tên vắc xin
    private String phongTriBenh;   // Phòng trị bệnh
    private int soLuong;           // Tổng số lượng các lô
    private String doTuoiTiemPhong;// Độ tuổi tiêm phòng
}