package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;

public interface BangGiaVacXinService {

	void softDeleteByVacXin(VacXinEntity vacXin);

	/**
	 * Lấy tất cả giá của 1 vắc xin theo thứ tự HieuLucTu giảm dần
	 * 
	 * @param maCode mã vắc xin
	 * @return danh sách BangGiaVacXinEntity
	 */
	List<BangGiaVacXinEntity> findByVacXinIdOrderByHieuLucTuDesc(String maCode);

	BangGiaVacXinEntity save(BangGiaVacXinEntity bangGiaVacXin);

}
