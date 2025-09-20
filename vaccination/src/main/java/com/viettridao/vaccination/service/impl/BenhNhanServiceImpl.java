package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.employee.UpdateBenhNhanRequest;
import com.viettridao.vaccination.dto.response.employee.UpdateBenhNhanResponse;
import com.viettridao.vaccination.mapper.BenhNhanMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.service.BenhNhanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BenhNhanServiceImpl implements BenhNhanService {
	private final BenhNhanRepository benhNhanRepository;
	private final BenhNhanMapper benhNhanMapper;

	@Override
	public List<BenhNhanEntity> getAllPatients() {
		return benhNhanRepository.findAll();
	}

	@Override
	public List<BenhNhanEntity> getAll() {
		return benhNhanRepository.findAll();
	}

	@Override
	public UpdateBenhNhanResponse getBenhNhanById(String maBenhNhan) {
		BenhNhanEntity entity = benhNhanRepository.findById(maBenhNhan)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + maBenhNhan));
		return benhNhanMapper.toResponse(entity);
	}

	@Override
	public List<UpdateBenhNhanResponse> getAllBenhNhan() {
		return benhNhanRepository.findAll().stream().map(benhNhanMapper::toResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UpdateBenhNhanResponse updateBenhNhan(UpdateBenhNhanRequest request) {
		BenhNhanEntity entity = benhNhanRepository.findById(request.getMaBenhNhan())
				.orElseThrow(() -> new RuntimeException("Bệnh nhân không tồn tại"));

		entity.setHoTen(request.getHoTen());
		entity.setGioiTinh(BenhNhanEntity.GioiTinh.valueOf(request.getGioiTinh().trim()));
		entity.setNgaySinh(java.time.LocalDate.now().minusYears(request.getTuoi()));
		entity.setTenNguoiGiamHo(request.getTenNguoiGiamHo());
		entity.setDiaChi(request.getDiaChi());
		entity.setSoDienThoai(request.getSoDienThoai());

		benhNhanRepository.save(entity);

		return benhNhanMapper.toResponse(entity);
	}

}
