package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.employee.KetQuaTiemRequest;
import com.viettridao.vaccination.dto.response.employee.KetQuaTiemResponse;

public interface KetQuaTiemService {

    KetQuaTiemResponse getKetQuaTiemById(String id);

    List<KetQuaTiemResponse> getAllKetQuaTiem();

    List<KetQuaTiemResponse> getKetQuaTiemByBenhNhan(String maBenhNhan);

    void deleteKetQuaTiem(String id);

    KetQuaTiemResponse createKetQuaTiem(KetQuaTiemRequest request);

    KetQuaTiemResponse getKetQuaTiemByTenBenhNhan(String tenBenhNhan);

    List<KetQuaTiemResponse> getKetQuaTiemHoanThanhByBenhNhan(String maBenhNhan);

    KetQuaTiemResponse getById(String id);

}
