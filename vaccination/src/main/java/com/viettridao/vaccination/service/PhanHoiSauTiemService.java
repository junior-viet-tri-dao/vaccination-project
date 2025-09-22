package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiSauTiemRequest;
import com.viettridao.vaccination.dto.response.normalUser.PhanHoiSauTiemResponse;

import java.util.List;

public interface PhanHoiSauTiemService {

    // Lấy danh sách các kết quả tiêm đã hoàn thành của bệnh nhân đang đăng nhập mà chưa phản hồi
    List<PhanHoiSauTiemResponse> getKetQuaTiemCanPhanHoiByCurrentUser();

    // Tạo phản hồi sau tiêm từ bệnh nhân đang đăng nhập
    void taoPhanHoiSauTiem(PhanHoiSauTiemRequest request);

    PhanHoiSauTiemResponse getByKetQuaTiemId(String ketQuaTiemId);


}