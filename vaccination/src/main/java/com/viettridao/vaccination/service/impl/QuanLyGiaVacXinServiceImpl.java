package com.viettridao.vaccination.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.finance.QuanLyGiaVacXinUpdateRequest;
import com.viettridao.vaccination.dto.response.finance.QuanLyGiaVacXinResponse;
import com.viettridao.vaccination.mapper.QuanLyGiaVacXinMapper;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.service.BangGiaVacXinService;
import com.viettridao.vaccination.service.BienDongKhoService;
import com.viettridao.vaccination.service.ChiTietHdNccService;
import com.viettridao.vaccination.service.GiaoDichKhachHangService;
import com.viettridao.vaccination.service.LoVacXinService;
import com.viettridao.vaccination.service.QuanLyGiaVacXinService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuanLyGiaVacXinServiceImpl implements QuanLyGiaVacXinService {

	private final LoVacXinService loVacXinService;
	private final QuanLyGiaVacXinMapper mapper;
	private final BienDongKhoService bienDongKhoService;
	private final BangGiaVacXinService bangGiaVacXinService;
	private final ChiTietHdNccService chiTietHdNccService;
	private final GiaoDichKhachHangService giaoDichKhachHangService;

	@Override
	public Page<QuanLyGiaVacXinResponse> getAllGiaVacXinHienTai(Pageable pageable) {
		List<LoVacXinEntity> loVacXins = loVacXinService.findAll(); // <-- dùng instance

		List<QuanLyGiaVacXinResponse> dtoList = loVacXins.stream().map(lv -> {
			BangGiaVacXinEntity bangGia = bangGiaVacXinService
					.findByVacXinIdOrderByHieuLucTuDesc(lv.getVacXin().getId()).stream()
					.filter(bg -> (bg.getHieuLucTu() == null || !bg.getHieuLucTu().isAfter(LocalDate.now()))
							&& (bg.getHieuLucDen() == null || !bg.getHieuLucDen().isBefore(LocalDate.now())))
					.findFirst().orElse(null);

			return mapper.toResponse(lv, bangGia);
		}).collect(Collectors.toList());

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), dtoList.size());
		List<QuanLyGiaVacXinResponse> content = dtoList.subList(start, end);

		return new PageImpl<>(content, pageable, dtoList.size());
	}

	public void updateGiaVacXin(QuanLyGiaVacXinUpdateRequest request) {
		LoVacXinEntity loVacXin = loVacXinService.findByVacXinMaCode(request.getMaCode())
				.orElseThrow(() -> new IllegalArgumentException("Mã vắc xin không tồn tại"));

		BangGiaVacXinEntity bangGia = bangGiaVacXinService
				.findByVacXinIdOrderByHieuLucTuDesc(loVacXin.getVacXin().getId()).stream().findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Giá vắc xin không tồn tại"));

		mapper.updateLoVacXinFromRequest(request, loVacXin);
		mapper.updateBangGiaFromRequest(request, bangGia);

		loVacXinService.save(loVacXin); // <-- dùng instance service
		bangGiaVacXinService.save(bangGia); // <-- dùng instance service
	}

	@Override
	public QuanLyGiaVacXinResponse getByMaCode(String maCode) {
		LoVacXinEntity loVacXin = loVacXinService.findByVacXinMaCode(maCode)
				.orElseThrow(() -> new IllegalArgumentException("Mã vắc xin không tồn tại"));

		BangGiaVacXinEntity bangGia = bangGiaVacXinService
				.findByVacXinIdOrderByHieuLucTuDesc(loVacXin.getVacXin().getId()).stream()
				.filter(bg -> (bg.getHieuLucTu() == null || !bg.getHieuLucTu().isAfter(LocalDate.now()))
						&& (bg.getHieuLucDen() == null || !bg.getHieuLucDen().isBefore(LocalDate.now())))
				.findFirst().orElse(null);

		return mapper.toResponse(loVacXin, bangGia);
	}

	@Transactional
	@Override
	public void deleteByMaCode(String maCode) {
		LoVacXinEntity loVacXin = loVacXinService.findByVacXinMaCode(maCode)
				.orElseThrow(() -> new IllegalArgumentException("Mã vắc xin không tồn tại"));

		// Xóa mềm các bảng con
		giaoDichKhachHangService.softDeleteByLoVacXin(loVacXin);
		chiTietHdNccService.softDeleteByLoVacXin(loVacXin);
		bienDongKhoService.softDeleteByLoVacXin(loVacXin);
		bangGiaVacXinService.softDeleteByVacXin(loVacXin.getVacXin());

		// Cuối cùng xóa mềm lô vắc xin
		loVacXin.setIsDeleted(true);
		loVacXinService.save(loVacXin);
	}
}