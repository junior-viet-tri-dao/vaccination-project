package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.response.DichBenhResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DichBenhService {
    /**
     * Lấy danh sách dịch bệnh có phân trang, chỉ lấy bản ghi chưa bị xóa mềm,
     * sắp xếp giảm dần theo thời điểm khảo sát.
     */
    Page<DichBenhResponse> getAllActiveDichBenh(Pageable pageable);
}