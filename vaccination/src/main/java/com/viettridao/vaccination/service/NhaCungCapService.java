package com.viettridao.vaccination.service;

import java.util.Optional;

import com.viettridao.vaccination.model.NhaCungCapEntity;

public interface NhaCungCapService {

	/**
	 * Lấy nhà cung cấp theo ID
	 * 
	 * @param id ID nhà cung cấp
	 * @return NhaCungCapEntity nếu tồn tại
	 */
	NhaCungCapEntity getById(String id);

	/**
	 * Lấy nhà cung cấp theo mã (nếu bạn thêm trường maCode)
	 * 
	 * @param maCode Mã nhà cung cấp
	 * @return Optional<NhaCungCapEntity>
	 */
	Optional<NhaCungCapEntity> getByMaCode(String maCode);

	/**
	 * Tạo mới nhà cung cấp
	 * 
	 * @param nhaCungCapEntity
	 * @return NhaCungCapEntity đã lưu
	 */
	NhaCungCapEntity create(NhaCungCapEntity nhaCungCapEntity);
}
