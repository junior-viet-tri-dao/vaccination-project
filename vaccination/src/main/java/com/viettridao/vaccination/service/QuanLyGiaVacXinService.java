package com.viettridao.vaccination.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.viettridao.vaccination.dto.request.finance.QuanLyGiaVacXinUpdateRequest;
import com.viettridao.vaccination.dto.response.finance.QuanLyGiaVacXinResponse;

public interface QuanLyGiaVacXinService {

	Page<QuanLyGiaVacXinResponse> getAllGiaVacXinHienTai(Pageable pageable);

	QuanLyGiaVacXinResponse getByMaCode(String maCode);

	void deleteByMaCode(String maCode);

	void updateGiaVacXin(QuanLyGiaVacXinUpdateRequest request);

	void createGiaVacXin(QuanLyGiaVacXinUpdateRequest request);

}
