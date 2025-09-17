package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.response.supportemployee.CauHoiTiemChungResponse;

public interface CauHoiTiemChungService {

	/**
	 * Lấy tất cả câu hỏi tư vấn tiêm chủng chưa bị xóa
	 */
	List<CauHoiTiemChungResponse> getAllCauHoi();

	/**
	 * Lấy chi tiết câu hỏi theo ID
	 */
	CauHoiTiemChungResponse getCauHoiById(String maCauHoi);

	/**
	 * Tạo câu hỏi mới
	 */
	CauHoiTiemChungResponse createCauHoi(String tieuDe, String noiDung);

	/**
	 * Cập nhật câu hỏi
	 */
	CauHoiTiemChungResponse updateCauHoi(String maCauHoi, String tieuDe, String noiDung);

	/**
	 * Xóa câu hỏi (soft delete)
	 */
	void deleteCauHoi(String maCauHoi);
}
