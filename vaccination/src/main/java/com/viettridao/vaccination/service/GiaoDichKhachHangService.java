package com.viettridao.vaccination.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.viettridao.vaccination.dto.request.finance.GiaoDichKhachHangRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichKhachHangResponse;
import com.viettridao.vaccination.model.LoVacXinEntity;

public interface GiaoDichKhachHangService {

	Page<GiaoDichKhachHangResponse> getAll(Pageable pageable);

	GiaoDichKhachHangResponse getByMaHoaDon(String maHoaDon);

	void create(GiaoDichKhachHangRequest request);

	void deleteByMaHoaDon(String maHoaDon);
	
    void softDeleteByLoVacXin(LoVacXinEntity loVacXin);

    void softDeleteByMaHoaDon(String maHoaDon);
    
    void update(GiaoDichKhachHangRequest request);
    
    int getGiaTheoMaVacXin(String maVacXin);

}
