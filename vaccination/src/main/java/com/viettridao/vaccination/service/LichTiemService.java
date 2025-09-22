package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.model.LichTiemEntity;

public interface LichTiemService {
	List<LichTiemEntity> getAllLichTiem();

	List<LichTiemEntity> getAll();

	LichTiemResponse createLichTiem(LichTiemRequest request);

	LichTiemResponse getLichTiemById(String maLich);

	List<LichTiemResponse> getDanhSachLichTiem();

	LichTiemResponse updateLichTiem(String maLich, LichTiemRequest request);

	void deleteLichTiem(String maLich);

	List<LichTiemResponse> getDanhSachLichTiemWithDonThuoc(String loaiVacXin);

	LichTiemResponse createOrUpdateLichTiem(LichTiemRequest request, String maLich);

	LichTiemRequest getLichTiemRequestById(String maLich);
	
    List<String> getTatCaLoaiVacXin(); // Thêm dòng này

    List<LichTiemResponse.DonThuocDTO> getDanhSachBenhNhanTheoLich(String maLich, String tenVacXin);

}
