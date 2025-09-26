package com.viettridao.vaccination.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse.DonThuocDto;
import com.viettridao.vaccination.mapper.LichTiemMapper;
import com.viettridao.vaccination.model.DonThuocEntity;
import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.DonThuocRepository;
import com.viettridao.vaccination.repository.LichTiemRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.service.LichTiemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LichTiemServiceImpl implements LichTiemService {

	private final LichTiemRepository lichTiemRepository;
	private final TaiKhoanRepository taiKhoanRepository;
	private final LichTiemMapper lichTiemMapper;
	private final DonThuocRepository donThuocRepository;
    private final VacXinRepository vacXinRepository; 


	@Override
	public List<LichTiemEntity> getAllLichTiem() {
		return lichTiemRepository.findAll();
	}

	@Override
	public List<LichTiemEntity> getAll() {
		return lichTiemRepository.findAll();
	}
	
	@Override
	public LichTiemResponse create(LichTiemRequest request, String taoBoiTenDangNhap) {
	    // Map từ request sang entity
	    LichTiemEntity entity = lichTiemMapper.toEntity(request);

	    // Lấy VacXin từ DB bằng ID
	    VacXinEntity vacXin = vacXinRepository.findById(request.getMaVacXin())
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy vắc xin với id: " + request.getMaVacXin()));
	    entity.setVacXin(vacXin);

	    // Lấy người tạo lịch theo username (tenDangNhap)
	    TaiKhoanEntity nguoiTao = taiKhoanRepository.findByTenDangNhap(taoBoiTenDangNhap)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản tạo lịch với username: " + taoBoiTenDangNhap));
	    entity.setTaoBoi(nguoiTao);

	    // Thời gian tạo và cờ xóa
	    entity.setNgayGio(request.getNgayGio());
	    entity.setNgayTao(LocalDateTime.now());
	    entity.setIsDeleted(false);

	    // Lưu và trả về response
	    return lichTiemMapper.toResponse(lichTiemRepository.save(entity));
	}



	@Override
	public LichTiemResponse update(String id, LichTiemRequest request) {
		LichTiemEntity entity = lichTiemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy lịch tiêm"));

		// cập nhật các trường
		entity.setNgayGio(request.getNgayGio());
		entity.setDiaDiem(request.getDiaDiem());
		entity.setSucChua(request.getSoLuong());

		return lichTiemMapper.toResponse(lichTiemRepository.save(entity));
	}

	@Override
	public void delete(String id) {
		LichTiemEntity entity = lichTiemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy lịch tiêm"));
		entity.setIsDeleted(true);
		lichTiemRepository.save(entity);
	}

	@Override
	public LichTiemResponse getById(String id) {
		LichTiemEntity entity = lichTiemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy lịch tiêm"));
		return lichTiemMapper.toResponse(entity);
	}

	@Override
	public List<LichTiemResponse> getAllLichTiemDangHoatDong() {
		return lichTiemMapper.toResponseList(lichTiemRepository.findAllByIsDeletedFalse());
	}

	@Override
	public List<DonThuocDto> getAllDonThuoc() {
	    List<DonThuocEntity> entities = donThuocRepository.findAll();
	    return entities.stream()
	            .map(d -> DonThuocDto.builder()
	                    .maDon(d.getId())
	                    .tenBenhNhan(d.getBenhNhan().getHoTen())
	                    .soDienThoai(d.getBenhNhan().getSoDienThoai())
	                    .henTiemLai(d.getHenTiemLai() != null ? d.getHenTiemLai().toLocalDate() : null)
	                    .tenVacXin(d.getVacXin().getTen())
	                    .build())
	            .collect(Collectors.toList());
	}

	
}
