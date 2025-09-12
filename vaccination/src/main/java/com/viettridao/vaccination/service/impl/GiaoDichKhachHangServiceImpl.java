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

		// 3️⃣ Tạo hóa đơn (lưu trước để có tham chiếu)
		HoaDonEntity hoaDon = new HoaDonEntity();
		hoaDon.setBenhNhan(benhNhan);
		hoaDon.setSoHoaDon(request.getSoHoaDon());
		hoaDon.setNgayHD(request.getNgayHD());
		hoaDon.setIsDeleted(false);
		hoaDon.setTongTien(0); // tạm
		hoaDonRepository.save(hoaDon);

		// 4️⃣ Chuẩn bị xuất: tổng cần xuất và biến chứa tổng tiền (Integer)
		int soLuongCanXuat = request.getSoLuong();
		int tongTien = 0;

		// Lưu ý: request.getGia() phải trả về Integer (hoặc bạn ép về int)
		int donGia = request.getGia();

		// 5️⃣ Lấy các lô theo hanSuDung tăng dần (lô sắp hết hạn trước)
		List<LoVacXinEntity> dsLo = loVacXinRepository.findByVacXinAndSoLuongGreaterThanOrderByHanSuDungAsc(vacXin, 0);

		for (LoVacXinEntity lo : dsLo) {
			if (soLuongCanXuat <= 0)
				break;

			int soLuongTru = Math.min(lo.getSoLuong(), soLuongCanXuat);
			int thanhTien = donGia * soLuongTru;

			// ➡️ Tạo chi tiết hóa đơn cho lô này
			ChiTietHDEntity chiTiet = new ChiTietHDEntity();
			chiTiet.setHoaDon(hoaDon);
			chiTiet.setVacXin(vacXin);
			chiTiet.setLoVacXin(lo);
			chiTiet.setSoLuong(soLuongTru);
			chiTiet.setDonGia(donGia);
			chiTiet.setThanhTien(thanhTien);
			chiTiet.setIsDeleted(false);
			chiTietHdRepository.save(chiTiet);

			// ➡️ Ghi biến động kho
			BienDongKhoEntity bienDong = new BienDongKhoEntity();
			bienDong.setLoVacXin(lo);
			bienDong.setLoaiBD(BienDongKhoEntity.LoaiBienDong.XUAT);
			bienDong.setSoLuong(soLuongTru);
			bienDong.setMaHoaDon(hoaDon.getSoHoaDon());
			bienDong.setLoaiHoaDon(BienDongKhoEntity.LoaiHoaDon.KHACH);
			bienDong.setNgayThucHien(LocalDateTime.now());
			bienDongKhoRepository.save(bienDong);

			// ➡️ Cập nhật số lượng lô
			lo.setSoLuong(lo.getSoLuong() - soLuongTru);
			loVacXinRepository.save(lo);

			// ➡️ Cộng dồn tiền và giảm số lượng cần xuất
			tongTien += thanhTien;
			soLuongCanXuat -= soLuongTru;
		}

		// 6️⃣ Nếu còn thiếu số lượng thì rollback (bằng exception) — @Transactional sẽ
		// rollback tự động
		if (soLuongCanXuat > 0) {
			throw new IllegalArgumentException("Không đủ số lượng trong kho");
		}

		// 7️⃣ Cập nhật tổng tiền (Integer) cho hóa đơn và lưu
		hoaDon.setTongTien(tongTien);
		hoaDonRepository.save(hoaDon);
	}

	@Transactional
	@Override
	public void update(GiaoDichKhachHangRequest request) {
		// Lấy hóa đơn hiện tại theo số hóa đơn
		HoaDonEntity hoaDon = hoaDonRepository.findBySoHoaDonAndIsDeletedFalse(request.getSoHoaDon())
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

		// Update các trường cơ bản trong hóa đơn
		hoaDon.setNgayHD(request.getNgayHD());
		hoaDonRepository.save(hoaDon);

		// Giả sử mỗi hóa đơn chỉ có 1 chi tiết, lấy chi tiết đầu tiên
		ChiTietHDEntity chiTiet = hoaDon.getChiTietHDs().stream().filter(ct -> !Boolean.TRUE.equals(ct.getIsDeleted()))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("Không có chi tiết hóa đơn"));

		// Update chi tiết
		chiTiet.setSoLuong(request.getSoLuong());
		chiTiet.setDonGia(request.getGia());
		VacXinEntity vacXin = vacXinRepository.findByMaCode(request.getMaVacXin())
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vắc xin"));
		chiTiet.setVacXin(vacXin);

		chiTietHdRepository.save(chiTiet);
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
