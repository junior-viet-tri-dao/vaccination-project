package com.viettridao.vaccination.service;


import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import org.springframework.data.domain.Page;

public interface VaccineListService {

    /**
     * Phân trang danh sách vắc xin, tìm kiếm theo mã, tên, hoặc phòng trị bệnh.
     * @param searchType - "maVacXin", "tenVacXin", "phongTriBenh"
     * @param keyword - từ khóa tìm kiếm
     * @param pageNo - trang hiện tại
     * @param pageSize - số phần tử/trang
     * @return Page<VaccineListResponse>
     */
    Page<VaccineListResponse> getVaccines(String searchType, String keyword, int pageNo, int pageSize);
}