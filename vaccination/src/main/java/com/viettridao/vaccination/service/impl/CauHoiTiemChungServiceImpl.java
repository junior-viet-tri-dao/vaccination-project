package com.viettridao.vaccination.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.response.supportemployee.CauHoiTiemChungResponse;
import com.viettridao.vaccination.mapper.CauHoiTiemChungMapper;
import com.viettridao.vaccination.model.PhanHoiEntity;
import com.viettridao.vaccination.model.PhanHoiEntity.LoaiPhanHoi;
import com.viettridao.vaccination.repository.PhanHoiRepository;
import com.viettridao.vaccination.service.CauHoiTiemChungService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CauHoiTiemChungServiceImpl implements CauHoiTiemChungService {

	private final PhanHoiRepository phanHoiRepository;
	private final CauHoiTiemChungMapper mapper;

	@Override
	public List<CauHoiTiemChungResponse> getAllCauHoi() {
		List<PhanHoiEntity> entities = phanHoiRepository.findByLoaiPhanHoiAndIsDeletedFalse(LoaiPhanHoi.CAU_HOI);
		return mapper.toResponseList(entities);
	}

	@Override
	public CauHoiTiemChungResponse getCauHoiById(String maCauHoi) {
		PhanHoiEntity entity = phanHoiRepository.findByIdAndIsDeletedFalse(maCauHoi)
				.orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));
		return mapper.toResponse(entity);
	}

	@Override
	public CauHoiTiemChungResponse createCauHoi(String tieuDe, String noiDung) {
		PhanHoiEntity entity = PhanHoiEntity.builder().tieuDe(tieuDe).noiDung(noiDung).loaiPhanHoi(LoaiPhanHoi.CAU_HOI)
				.isDeleted(false).ngayTao(LocalDateTime.now()).build();
		entity = phanHoiRepository.save(entity);
		return mapper.toResponse(entity);
	}

	@Override
	public CauHoiTiemChungResponse updateCauHoi(String maCauHoi, String tieuDe, String noiDung) {
		PhanHoiEntity entity = phanHoiRepository.findByIdAndIsDeletedFalse(maCauHoi)
				.orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));
		entity.setTieuDe(tieuDe);
		entity.setNoiDung(noiDung);
		entity = phanHoiRepository.save(entity);
		return mapper.toResponse(entity);
	}

	@Override
	public void deleteCauHoi(String maCauHoi) {
	    PhanHoiEntity entity = phanHoiRepository.findByIdAndIsDeletedFalse(maCauHoi)
	            .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));
	    entity.setIsDeleted(true); // đánh dấu xóa mềm
	    phanHoiRepository.save(entity);
	}

}
