package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.finance.GiaoDichKhachHangRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichKhachHangResponse;
import com.viettridao.vaccination.mapper.GiaoDichKhachHangMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.ChiTietHDEntity;
import com.viettridao.vaccination.model.HoaDonEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.ChiTietHdRepository;
import com.viettridao.vaccination.repository.HoaDonRepository;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.service.GiaoDichKhachHangService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GiaoDichKhachHangServiceImpl implements GiaoDichKhachHangService {

	private final HoaDonRepository hoaDonRepository;
	private final ChiTietHdRepository chiTietHdRepository;
	private final BenhNhanRepository benhNhanRepository;
	private final VacXinRepository vacXinRepository;
	private final GiaoDichKhachHangMapper mapper;

	@Override
	public Page<GiaoDichKhachHangResponse> getAll(Pageable pageable) {
		List<HoaDonEntity> hoaDons = hoaDonRepository.findAllByIsDeletedFalse();

		List<GiaoDichKhachHangResponse> dtoList = hoaDons
				.stream().flatMap(hd -> hd.getChiTietHDs().stream()
						.filter(ct -> !Boolean.TRUE.equals(ct.getIsDeleted())).map(ct -> mapper.toResponse(hd, ct)))
				.collect(Collectors.toList());

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), dtoList.size());
		List<GiaoDichKhachHangResponse> content = dtoList.subList(start, end);

		return new PageImpl<>(content, pageable, dtoList.size());
	}

	@Override
	public GiaoDichKhachHangResponse getByMaHoaDon(String maHoaDon) {
		HoaDonEntity hoaDon = hoaDonRepository.findByMaHoaDonAndIsDeletedFalse(maHoaDon)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

		ChiTietHDEntity chiTiet = hoaDon.getChiTietHDs().stream().filter(ct -> !Boolean.TRUE.equals(ct.getIsDeleted()))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("Không có chi tiết hóa đơn"));

		return mapper.toResponse(hoaDon, chiTiet);
	}

	@Transactional
	@Override
	public void create(GiaoDichKhachHangRequest request) {
	    // Lấy khách hàng từ DB
	    BenhNhanEntity benhNhan = benhNhanRepository.findByHoTen(request.getTenKhachHang())
	            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng"));

	    VacXinEntity vacXin = vacXinRepository.findByMaCode(request.getMaVacXin())
	            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vắc xin"));

	    // Mapper chỉ map các field khác, không map benhNhan
	    HoaDonEntity hoaDon = mapper.toHoaDonEntity(request);
	    hoaDon.setBenhNhan(benhNhan);

	    ChiTietHDEntity chiTiet = mapper.toChiTietEntity(request);
	    chiTiet.setVacXin(vacXin);
	    chiTiet.setHoaDon(hoaDon);

	    hoaDonRepository.save(hoaDon);
	    chiTietHdRepository.save(chiTiet);
	}


	@Transactional
	@Override
	public void deleteByMaHoaDon(String maHoaDon) {
		HoaDonEntity hoaDon = hoaDonRepository.findByMaHoaDonAndIsDeletedFalse(maHoaDon)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

		hoaDon.getChiTietHDs().forEach(ct -> {
			ct.setIsDeleted(true);
			chiTietHdRepository.save(ct);
		});

		hoaDon.setIsDeleted(true);
		hoaDonRepository.save(hoaDon);
	}
	
	
	

	@Transactional
	@Override
	public void softDeleteByLoVacXin(LoVacXinEntity loVacXin) {
		List<ChiTietHDEntity> list = chiTietHdRepository.findAllByLoVacXinAndIsDeletedFalse(loVacXin);
		list.forEach(ct -> ct.setIsDeleted(true));
		chiTietHdRepository.saveAll(list);
	}

	@Transactional
	@Override
	public void softDeleteByMaHoaDon(String maHoaDon) {
		HoaDonEntity hoaDon = hoaDonRepository.findByMaHoaDonAndIsDeletedFalse(maHoaDon)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

		hoaDon.getChiTietHDs().forEach(ct -> {
			ct.setIsDeleted(true);
			chiTietHdRepository.save(ct);
		});

		hoaDon.setIsDeleted(true);
		hoaDonRepository.save(hoaDon);
	}
}
