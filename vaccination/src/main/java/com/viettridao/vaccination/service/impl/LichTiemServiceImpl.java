package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.mapper.LichTiemMapper;
import com.viettridao.vaccination.model.DonThuocEntity;
import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.repository.DonThuocRepository;
import com.viettridao.vaccination.repository.LichTiemRepository;
import com.viettridao.vaccination.service.LichTiemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LichTiemServiceImpl implements LichTiemService {

	private final LichTiemRepository lichTiemRepository;
	private final DonThuocRepository donThuocRepository;
	private final LichTiemMapper lichTiemMapper;

	@Override
	public List<LichTiemEntity> getAllLichTiem() {
		return lichTiemRepository.findAll();
	}

	@Override
	public List<LichTiemEntity> getAll() {
		return lichTiemRepository.findAll();
	}

	@Override
	public LichTiemResponse createLichTiem(LichTiemRequest request) {
		LichTiemEntity entity = lichTiemMapper.toEntity(request);
		// TODO: set vacXin, taoBoi, other relations
		LichTiemEntity saved = lichTiemRepository.save(entity);
		return lichTiemMapper.toResponse(saved);
	}

	@Override
	public LichTiemResponse getLichTiemById(String maLich) {
		LichTiemEntity entity = lichTiemRepository.findById(maLich)
				.orElseThrow(() -> new RuntimeException("Lịch tiêm không tồn tại"));
		return lichTiemMapper.toResponse(entity);
	}

	@Override
	public List<LichTiemResponse> getDanhSachLichTiem() {
		List<LichTiemEntity> list = lichTiemRepository.findByIsDeletedFalse();
		return list.stream().map(lichTiemMapper::toResponse).collect(Collectors.toList());
	}

	@Override
	public LichTiemResponse updateLichTiem(String maLich, LichTiemRequest request) {
		LichTiemEntity entity = lichTiemRepository.findById(maLich)
				.orElseThrow(() -> new RuntimeException("Lịch tiêm không tồn tại"));

		// update các field
		entity.setNgayGio(request.getNgayGio());
		entity.setDiaDiem(request.getDiaDiem());
		entity.setMoTa(request.getMoTa());
		entity.setSucChua(request.getSucChua());
		entity.setTieuDe(request.getTieuDe());
		// TODO: cập nhật vacXin, taoBoi nếu cần

		LichTiemEntity updated = lichTiemRepository.save(entity);
		return lichTiemMapper.toResponse(updated);
	}

	@Override
	public void deleteLichTiem(String maLich) {
		LichTiemEntity entity = lichTiemRepository.findById(maLich)
				.orElseThrow(() -> new RuntimeException("Lịch tiêm không tồn tại"));
		entity.setIsDeleted(true);
		lichTiemRepository.save(entity);
	}

	@Override
	public List<LichTiemResponse> getDanhSachLichTiemWithDonThuoc(String loaiVacXin) {
		List<LichTiemEntity> lichList = lichTiemRepository.findByIsDeletedFalse();
		return lichList.stream().map(lich -> {
			LichTiemResponse response = lichTiemMapper.toResponse(lich);
			if (loaiVacXin != null) {
				List<LichTiemResponse.DonThuocDTO> filtered = response.getDanhSachDonThuoc().stream()
						.filter(d -> d.getTenVacXin().equalsIgnoreCase(loaiVacXin)).toList();
				response.setDanhSachDonThuoc(filtered);
			}
			return response;
		}).toList();
	}

	@Override
	public LichTiemResponse createOrUpdateLichTiem(LichTiemRequest request, String maLich) {
		LichTiemEntity entity;
		if (maLich == null) { // create
			entity = lichTiemMapper.toEntity(request);
		} else { // update
			entity = lichTiemRepository.findById(maLich)
					.orElseThrow(() -> new RuntimeException("Lịch tiêm không tồn tại"));
			entity.setNgayGio(request.getNgayGio());
			entity.setDiaDiem(request.getDiaDiem());
			entity.setMoTa(request.getMoTa());
			entity.setSucChua(request.getSoLuong());
			entity.setTieuDe(request.getTieuDe());
			// TODO: set vacXin, taoBoi nếu cần
		}
		LichTiemEntity saved = lichTiemRepository.save(entity);
		return lichTiemMapper.toResponse(saved);
	}

	@Override
	public LichTiemRequest getLichTiemRequestById(String maLich) {
		LichTiemResponse lich = lichTiemMapper.toResponse(
				lichTiemRepository.findById(maLich).orElseThrow(() -> new RuntimeException("Lịch tiêm không tồn tại")));
		return LichTiemRequest.builder().ngayGio(lich.getNgayGio()).diaDiem(lich.getDiaDiem()).moTa(lich.getMoTa())
				.soLuong(lich.getSucChua()).tieuDe(lich.getTieuDe()).maVacXin(lich.getLoaiVacXin())
				.loaiVacXin(lich.getLoaiVacXin()).build();
	}
	
	public List<String> getTatCaLoaiVacXin() {
	    return lichTiemRepository.findDistinctLoaiVacXin(); 
	}
	
	@Override
    public List<LichTiemResponse.DonThuocDTO> getDanhSachBenhNhanTheoLich(String maLich, String tenVacXin) {
        List<DonThuocEntity> donThuocs;
        if (tenVacXin == null || tenVacXin.isEmpty()) {
            donThuocs = donThuocRepository.findByLichTiem_Id(maLich);
        } else {
            donThuocs = donThuocRepository.findByLichTiem_IdAndVacXin_Ten(maLich, tenVacXin);
        }
        return lichTiemMapper.donThuocEntityListToDTOList(donThuocs);
    }

}
