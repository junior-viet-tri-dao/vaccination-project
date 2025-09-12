package com.viettridao.vaccination.service.impl;

import java.time.LocalDateTime;
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
import com.viettridao.vaccination.model.BienDongKhoEntity;
import com.viettridao.vaccination.model.ChiTietHDEntity;
import com.viettridao.vaccination.model.HoaDonEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.BienDongKhoRepository;
import com.viettridao.vaccination.repository.ChiTietHdRepository;
import com.viettridao.vaccination.repository.HoaDonRepository;
import com.viettridao.vaccination.repository.LoVacXinRepository;
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
	private final LoVacXinRepository loVacXinRepository;
	private final BienDongKhoRepository bienDongKhoRepository;

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
	@Transactional(readOnly = true)
	public GiaoDichKhachHangResponse getByMaHoaDon(String soHoaDon) {
		HoaDonEntity hoaDon = hoaDonRepository.findBySoHoaDonAndIsDeletedFalse(soHoaDon)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

		ChiTietHDEntity chiTiet = hoaDon.getChiTietHDs().stream().filter(ct -> !Boolean.TRUE.equals(ct.getIsDeleted()))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("Không có chi tiết hóa đơn"));

		return mapper.toResponse(hoaDon, chiTiet);
	}

	@Override
	@Transactional
	public void create(GiaoDichKhachHangRequest request) {
		// 1️⃣ Lấy khách hàng hoặc tạo mới nếu chưa có
		BenhNhanEntity benhNhan = benhNhanRepository.findByHoTen(request.getTenKhachHang()).orElseGet(() -> {
			BenhNhanEntity newBn = new BenhNhanEntity();
			newBn.setHoTen(request.getTenKhachHang());
			return benhNhanRepository.save(newBn);
		});

		// 2️⃣ Lấy vắc xin
		VacXinEntity vacXin = vacXinRepository.findByMaCode(request.getMaVacXin())
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vắc xin"));

		// 3️⃣ Tìm lô vắc xin còn đủ số lượng
		LoVacXinEntity loVacXin = loVacXinRepository
				.findFirstByVacXinAndSoLuongGreaterThan(vacXin, request.getSoLuong())
				.orElseThrow(() -> new IllegalArgumentException("Không còn lô vắc xin đủ số lượng"));

		// 4️⃣ Tạo hóa đơn
		HoaDonEntity hoaDon = new HoaDonEntity();
		hoaDon.setBenhNhan(benhNhan);
		hoaDon.setSoHoaDon(request.getSoHoaDon());
		hoaDon.setNgayHD(request.getNgayHD());
		hoaDon.setTongTien(request.getGia() * request.getSoLuong());
		hoaDon.setIsDeleted(false);
		hoaDonRepository.save(hoaDon);

		// 5️⃣ Tạo chi tiết hóa đơn
		ChiTietHDEntity chiTiet = new ChiTietHDEntity();
		chiTiet.setHoaDon(hoaDon);
		chiTiet.setVacXin(vacXin);
		chiTiet.setLoVacXin(loVacXin);
		chiTiet.setSoLuong(request.getSoLuong());
		chiTiet.setDonGia(request.getGia());
		chiTiet.setThanhTien(request.getGia() * request.getSoLuong());
		chiTiet.setIsDeleted(false);
		chiTietHdRepository.save(chiTiet);

		// 6️⃣ Cập nhật biến động kho (trừ số lượng)
		BienDongKhoEntity bienDong = new BienDongKhoEntity();
		bienDong.setLoVacXin(loVacXin);
		bienDong.setLoaiBD(BienDongKhoEntity.LoaiBienDong.XUAT);
		bienDong.setSoLuong(request.getSoLuong());
		bienDong.setMaHoaDon(hoaDon.getSoHoaDon());
		bienDong.setLoaiHoaDon(BienDongKhoEntity.LoaiHoaDon.KHACH);
		bienDong.setNgayThucHien(LocalDateTime.now());
		bienDongKhoRepository.save(bienDong);

		// 7️⃣ Cập nhật lại số lượng lô
		loVacXin.setSoLuong(loVacXin.getSoLuong() - request.getSoLuong());
		loVacXinRepository.save(loVacXin);
	}

	@Transactional
	@Override
	public void deleteByMaHoaDon(String soHoaDon) {
		HoaDonEntity hoaDon = hoaDonRepository.findBySoHoaDonAndIsDeletedFalse(soHoaDon)
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
	public void softDeleteByMaHoaDon(String soHoaDon) {
		HoaDonEntity hoaDon = hoaDonRepository.findBySoHoaDonAndIsDeletedFalse(soHoaDon)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

		hoaDon.getChiTietHDs().forEach(ct -> {
			ct.setIsDeleted(true);
			chiTietHdRepository.save(ct);
		});

		hoaDon.setIsDeleted(true);
		hoaDonRepository.save(hoaDon);
	}
}
