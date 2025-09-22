package com.viettridao.vaccination.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.viettridao.vaccination.dto.request.finance.GiaoDichNhaCungCapRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichNhaCungCapResponse;

public interface GiaoDichNccService {
	List<GiaoDichNhaCungCapResponse> getAll();

	Page<GiaoDichNhaCungCapResponse> getAll(Pageable pageable);

	GiaoDichNhaCungCapResponse create(GiaoDichNhaCungCapRequest request);

	GiaoDichNhaCungCapResponse update(GiaoDichNhaCungCapRequest request);

    void softDeleteBySoHoaDon(String soHoaDon);
    
    GiaoDichNhaCungCapResponse getBySoHoaDon(String soHoaDon);

    GiaoDichNhaCungCapRequest buildUpdateRequest(String soHoaDon);

}
