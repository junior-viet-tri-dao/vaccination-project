package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.response.EpidemicResponse;
import org.springframework.data.domain.Page;

/**
 * EpidemicService
 * Service interface xử lý nghiệp vụ liên quan đến Epidemic.
 */
public interface EpidemicService {

    /**
     * Lấy danh sách Epidemic có phân trang.
     *
     * @param page số trang (bắt đầu từ 0)
     * @param size số bản ghi mỗi trang
     * @return Page<EpidemicResponse>
     */
    Page<EpidemicResponse> getAllEpidemics(int page, int size);
}
