package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse.DonThuocDto;
import com.viettridao.vaccination.model.LichTiemEntity;

public interface LichTiemService {
    List<LichTiemEntity> getAllLichTiem();

    List<LichTiemEntity> getAll();

    LichTiemResponse create(LichTiemRequest request, String taoBoiId);

    LichTiemResponse update(String id, LichTiemRequest request);

    void delete(String id);

    LichTiemResponse getById(String id);

    List<LichTiemResponse> getAllLichTiemDangHoatDong();

    List<DonThuocDto> getAllDonThuoc();


}
