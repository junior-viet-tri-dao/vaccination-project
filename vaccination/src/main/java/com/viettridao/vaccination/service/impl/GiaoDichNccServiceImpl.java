package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.finance.GiaoDichNhaCungCapRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichNhaCungCapResponse;
import com.viettridao.vaccination.mapper.GiaoDichNccMapper;
import com.viettridao.vaccination.model.ChiTietHDNCCEntity;
import com.viettridao.vaccination.model.HoaDonNCCEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.NhaCungCapEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.ChiTietHdNccRepository;
import com.viettridao.vaccination.repository.HoaDonNccRepository;
import com.viettridao.vaccination.repository.LoVacXinRepository;
import com.viettridao.vaccination.repository.NhaCungCapRepository;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.service.GiaoDichNccService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GiaoDichNccServiceImpl implements GiaoDichNccService {

	private final ChiTietHdNccRepository chiTietRepo;
	private final GiaoDichNccMapper mapper;
	private final HoaDonNccRepository hoaDonNCCRepository;
	private final VacXinRepository vacXinRepository;
	private final LoVacXinRepository loVacXinRepository;
	private final NhaCungCapRepository nhaCungCapRepository;

	// Lấy tất cả giao dịch chưa xóa theo trang
	@Override
	public Page<GiaoDichNhaCungCapResponse> getAll(Pageable pageable) {
		List<GiaoDichNhaCungCapResponse> all = chiTietRepo.findAll().stream()
				.filter(c -> c.getIsDeleted() == null || !c.getIsDeleted()).map(mapper::toResponse)
				.collect(Collectors.toList());

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), all.size());
		List<GiaoDichNhaCungCapResponse> content = all.subList(start, end);

		return new PageImpl<>(content, pageable, all.size());
	}

	// Lấy tất cả giao dịch chưa xóa (không phân trang)
	@Override
	public List<GiaoDichNhaCungCapResponse> getAll() {
		return chiTietRepo.findAll().stream().filter(c -> c.getIsDeleted() == null || !c.getIsDeleted())
				.map(mapper::toResponse).toList();
	}

	// Lấy một giao dịch theo mã hóa đơn
	@Override
	public GiaoDichNhaCungCapResponse getBySoHoaDon(String soHoaDon) {
		return chiTietRepo.findByHoaDonNCCSoHoaDonAndIsDeletedFalse(soHoaDon).stream().findFirst()
				.map(mapper::toResponse)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch với mã: " + soHoaDon));
	}

	// Tạo mới giao dịch
	@Transactional
	@Override
	public GiaoDichNhaCungCapResponse create(GiaoDichNhaCungCapRequest request) {
		NhaCungCapEntity nhaCungCap = nhaCungCapRepository.findByTen(request.getTenNhaCungCap())
				.orElseGet(() -> nhaCungCapRepository
						.save(NhaCungCapEntity.builder().ten(request.getTenNhaCungCap()).isDeleted(false).build()));

		HoaDonNCCEntity hoaDon = hoaDonNCCRepository.findBySoHoaDon(request.getSoHoaDon())
				.orElseGet(() -> hoaDonNCCRepository.save(HoaDonNCCEntity.builder().soHoaDon(request.getSoHoaDon())
						.ngayHD(request.getNgay()).nhaCungCap(nhaCungCap).isDeleted(false).build()));

		VacXinEntity vacXin = vacXinRepository.findByMaCode(request.getMaVacXin()).orElseGet(() -> vacXinRepository
				.save(VacXinEntity.builder().maCode(request.getMaVacXin()).ten(request.getTenVacXin()).isDeleted(false).build()));


		LoVacXinEntity loVacXin = loVacXinRepository.findByMaLoCodeIgnoreCaseAndIsDeletedFalse(request.getMaLo())
				.orElseGet(() -> loVacXinRepository.save(LoVacXinEntity.builder().maLoCode(request.getMaLo())
						.vacXin(vacXin).soLuong(request.getSoLuong()).isDeleted(false).build()));

		ChiTietHDNCCEntity chiTiet = mapper.toEntity(request);
		chiTiet.setHoaDonNCC(hoaDon);
		chiTiet.setVacXin(vacXin);
		chiTiet.setLoVacXin(loVacXin);
		chiTiet.setIsDeleted(false);

		return mapper.toResponse(chiTietRepo.save(chiTiet));
	}

	// Cập nhật giao dịch
	@Transactional
	@Override
	public GiaoDichNhaCungCapResponse update(GiaoDichNhaCungCapRequest request) {
		// 1. Tìm chi tiết hóa đơn theo số hóa đơn
		ChiTietHDNCCEntity chiTiet = chiTietRepo.findByHoaDonNCCSoHoaDonAndIsDeletedFalse(request.getSoHoaDon())
				.stream().findFirst().orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch để cập nhật"));

		// 2. Cập nhật các trường được phép sửa
		chiTiet.setSoLuong(request.getSoLuong());
		chiTiet.setDonGia(request.getGia());
		chiTiet.setIsDeleted(false);

		// 3. Cập nhật Ngày hóa đơn
		HoaDonNCCEntity hoaDon = hoaDonNCCRepository.findBySoHoaDon(request.getSoHoaDon())
				.orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn: " + request.getSoHoaDon()));
		hoaDon.setNgayHD(request.getNgay());

		// 4. Cập nhật Tên vắc xin (không thay đổi mã vắc xin)
		if (request.getTenVacXin() != null && !request.getTenVacXin().isBlank()) {
			VacXinEntity vacXin = chiTiet.getVacXin();
			vacXin.setTen(request.getTenVacXin());
			chiTiet.setVacXin(vacXin);
		}

		// 5. Cập nhật Nhà cung cấp (nếu có)
		if (request.getTenNhaCungCap() != null && !request.getTenNhaCungCap().isBlank()) {
			NhaCungCapEntity nhaCungCap = nhaCungCapRepository.findByTen(request.getTenNhaCungCap()).orElseThrow(
					() -> new RuntimeException("Không tìm thấy nhà cung cấp: " + request.getTenNhaCungCap()));
			hoaDon.setNhaCungCap(nhaCungCap);
		}

		// 6. Lưu lại thay đổi
		ChiTietHDNCCEntity saved = chiTietRepo.save(chiTiet);

		// 7. Convert sang response
		return mapper.toResponse(saved);
	}

	// Xóa mềm theo mã hóa đơn
	@Transactional
	@Override
	public void softDeleteBySoHoaDon(String soHoaDon) {
		List<ChiTietHDNCCEntity> chiTiets = chiTietRepo.findByHoaDonNCCSoHoaDonAndIsDeletedFalse(soHoaDon);

		chiTiets.forEach(c -> c.setIsDeleted(true));
		chiTietRepo.saveAll(chiTiets);
	}
}
