package com.viettridao.vaccination.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.viettridao.vaccination.dto.request.finance.GiaoDichKhachHangRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichKhachHangResponse;
import com.viettridao.vaccination.mapper.GiaoDichKhachHangMapper;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.BienDongKhoEntity;
import com.viettridao.vaccination.model.ChiTietHDEntity;
import com.viettridao.vaccination.model.HoaDonEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.BangGiaVacXinRepository;
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
	private final BangGiaVacXinRepository bangGiaVacXinRepository;
	
	
	 public Map<String, Integer> getVaccinePriceMap() {
	        List<VacXinEntity> vaccines = vacXinRepository.findAll();
	        return vaccines.stream()
	                .collect(Collectors.toMap(
	                        VacXinEntity::getMaCode,
	                        v -> getGiaTheoMaVacXin(v.getMaCode())
	                ));
	    }

	    // Build request mặc định khi create
	    public GiaoDichKhachHangRequest buildCreateRequest(String maVacXin) {
	        GiaoDichKhachHangRequest dto = new GiaoDichKhachHangRequest();
	        dto.setNgayHD(LocalDateTime.now());

	        if (maVacXin != null && !maVacXin.isEmpty()) {
	            dto.setMaVacXin(maVacXin);
	            dto.setGia(getGiaTheoMaVacXin(maVacXin));
	        }
	        return dto;
	    }

	    // Build request khi update
	    public GiaoDichKhachHangRequest buildUpdateRequest(String soHoaDon) {
	        GiaoDichKhachHangResponse transaction = getByMaHoaDon(soHoaDon);

	        GiaoDichKhachHangRequest request = new GiaoDichKhachHangRequest();
	        request.setNgayHD(transaction.getNgayHD());
	        request.setSoHoaDon(transaction.getSoHoaDon());
	        request.setMaVacXin(transaction.getMaVacXin());
	        request.setSoLuong(transaction.getSoLuong());
	        request.setTenKhachHang(transaction.getTenKhachHang());
	        request.setGia(transaction.getGia() != null ? transaction.getGia() : 0);

	        return request;
	    }

	    // Xử lý lỗi khi create
	    public void handleCreateError(Exception e, Model model, GiaoDichKhachHangRequest request) {
	        String msg = e.getMessage();
	        if (msg.contains("MaLo")) {
	            model.addAttribute("maLoError", msg);
	        } else if (msg.contains("SoHoaDon")) {
	            model.addAttribute("soHoaDonError", msg);
	        } else {
	            model.addAttribute("globalError", msg);
	        }

	        model.addAttribute("transactionRequest", request);
	        model.addAttribute("vaccines", vacXinRepository.findAll());
	        // ⚡ nếu muốn lấy patients thì inject BenhNhanService vào đây hoặc để controller set thêm
	    }

	@Override
	public Page<GiaoDichKhachHangResponse> getAll(Pageable pageable) {
		// 1️⃣ Lấy toàn bộ hóa đơn chưa bị xóa, sắp xếp theo ngày mới nhất
		List<HoaDonEntity> hoaDons = hoaDonRepository.findAllByIsDeletedFalse().stream()
				.sorted(Comparator.comparing(HoaDonEntity::getNgayHD, Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();

		// 2️⃣ Lấy toàn bộ bảng giá vắc xin một lần
		List<BangGiaVacXinEntity> bangGiaList = bangGiaVacXinRepository.findAll();

		// 3️⃣ Map từng chi tiết hóa đơn thành DTO
		List<GiaoDichKhachHangResponse> dtoList = hoaDons.stream().flatMap(
				hd -> hd.getChiTietHDs().stream().filter(ct -> !Boolean.TRUE.equals(ct.getIsDeleted())).map(ct -> {
					// Lấy giá mới nhất theo vacXin của chi tiết
					BangGiaVacXinEntity bangGia = bangGiaList.stream()
							.filter(bg -> bg.getVacXin().getId().equals(ct.getVacXin().getId()))
							.max(Comparator.comparing(BangGiaVacXinEntity::getNgayTao,
									Comparator.nullsLast(Comparator.naturalOrder())))
							.orElse(null);

					// Mapper chi tiết hóa đơn + hóa đơn
					return mapper.toResponse(hd, ct);
				})).toList();

		// 4️⃣ Phân trang thủ công
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

		return mapper.toResponse(hoaDon, chiTiet); // <- chỉ 2 tham số
	}

	@Transactional(readOnly = true)
	public int getGiaTheoMaVacXin(String maCode) {
		return bangGiaVacXinRepository.findByVacXinMaCodeOrderByHieuLucTuDesc(maCode).stream().findFirst()
				.map(BangGiaVacXinEntity::getGia).orElse(0);
	}

	@Transactional
	@Override
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

		// 🔎 Lấy giá mới nhất từ bảng giá
		int donGia = bangGiaVacXinRepository.findByVacXinIdOrderByHieuLucTuDesc(vacXin.getId()).stream().findFirst()
				.map(BangGiaVacXinEntity::getGia)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giá cho vắc xin"));

		// 3️⃣ Tạo hóa đơn
		HoaDonEntity hoaDon = new HoaDonEntity();
		hoaDon.setBenhNhan(benhNhan);
		hoaDon.setSoHoaDon(request.getSoHoaDon());
		hoaDon.setNgayHD(request.getNgayHD());
		hoaDon.setIsDeleted(false);
		hoaDon.setTongTien(0); // tạm
		hoaDonRepository.save(hoaDon);

		// 4️⃣ Chuẩn bị xuất: tổng cần xuất và tổng tiền
		int soLuongCanXuat = request.getSoLuong();
		int tongTien = 0;

		// 5️⃣ Lấy các lô theo hanSuDung tăng dần
		List<LoVacXinEntity> dsLo = loVacXinRepository.findByVacXinAndSoLuongGreaterThanOrderByHanSuDungAsc(vacXin, 0);

		for (LoVacXinEntity lo : dsLo) {
			if (soLuongCanXuat <= 0)
				break;

			int soLuongTru = Math.min(lo.getSoLuong(), soLuongCanXuat);
			int thanhTien = donGia * soLuongTru;

			// ➡️ Tạo chi tiết hóa đơn
			ChiTietHDEntity chiTiet = new ChiTietHDEntity();
			chiTiet.setHoaDon(hoaDon);
			chiTiet.setVacXin(vacXin);
			chiTiet.setLoVacXin(lo);
			chiTiet.setSoLuong(soLuongTru);
			chiTiet.setDonGia(donGia);
			chiTiet.setThanhTien(thanhTien);
			chiTiet.setIsDeleted(false);
			chiTietHdRepository.save(chiTiet);

			// ➡️ Biến động kho
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

		if (soLuongCanXuat > 0) {
			throw new IllegalArgumentException("Không đủ số lượng trong kho");
		}

		// 6️⃣ Cập nhật tổng tiền
		hoaDon.setTongTien(tongTien);
		hoaDonRepository.save(hoaDon);
	}

	@Transactional
	@Override
	public void update(GiaoDichKhachHangRequest request) {
		// 1️⃣ Lấy hóa đơn theo số hóa đơn
		HoaDonEntity hoaDon = hoaDonRepository.findBySoHoaDonAndIsDeletedFalse(request.getSoHoaDon())
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

		// 2️⃣ Lấy chi tiết hóa đơn (giả sử mỗi hóa đơn 1 chi tiết)
		ChiTietHDEntity chiTiet = hoaDon.getChiTietHDs().stream().filter(ct -> !Boolean.TRUE.equals(ct.getIsDeleted()))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("Không có chi tiết hóa đơn"));

		// 3️⃣ Kiểm tra xem có thay đổi vắc xin hoặc số lượng
		boolean vacXinChanged = !chiTiet.getVacXin().getMaCode().equals(request.getMaVacXin());
		boolean soLuongChanged = chiTiet.getSoLuong() != request.getSoLuong();

		if (!vacXinChanged && !soLuongChanged) {
			// Không có gì thay đổi → không update
			return;
		}

		// 4️⃣ Lấy vắc xin mới (nếu thay đổi)
		VacXinEntity vacXin = vacXinRepository.findByMaCode(request.getMaVacXin())
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vắc xin"));

		chiTiet.setVacXin(vacXin);

		// 5️⃣ Lấy giá mới nhất từ bảng giá
		BangGiaVacXinEntity bangGia = bangGiaVacXinRepository.findByVacXinIdOrderByHieuLucTuDesc(vacXin.getId())
				.stream().findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giá cho vắc xin này"));

		int donGia = bangGia.getGia();

		// 6️⃣ Cập nhật số lượng, giá và thành tiền
		chiTiet.setSoLuong(request.getSoLuong());
		chiTiet.setDonGia(donGia);
		chiTiet.setThanhTien(donGia * request.getSoLuong());
		chiTietHdRepository.save(chiTiet);

		// 7️⃣ Cập nhật tổng tiền hóa đơn
		hoaDon.setTongTien(chiTiet.getThanhTien());
		hoaDonRepository.save(hoaDon);
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
