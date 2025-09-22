package com.viettridao.vaccination.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.BangGiaVacXinRepository;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.service.BangGiaVacXinService;
import com.viettridao.vaccination.service.BienDongKhoService;
import com.viettridao.vaccination.service.ChiTietHdNccService;
import com.viettridao.vaccination.service.GiaoDichKhachHangService;
import com.viettridao.vaccination.service.LoVacXinService;
import com.viettridao.vaccination.service.QuanLyGiaVacXinService;
import com.viettridao.vaccination.service.VacXinService;

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
	private final VacXinService vacXinService;
    private final VacXinRepository vacXinRepository;
    private final BangGiaVacXinRepository bangGiaVacXinRepository;


	
	
	 public List<Map<String, Object>> buildVaccineDataForJs() {
		 List<VacXinEntity> vaccines = vacXinRepository.findByIsDeletedFalse();
	        return vaccines.stream().map(vx -> {
	            LoVacXinEntity lo = vx.getLoVacXins().stream().findFirst().orElse(null);
	            Integer gia = bangGiaVacXinRepository.findByVacXinIdOrderByHieuLucTuDesc(vx.getId())
	                    .stream().findFirst().map(BangGiaVacXinEntity::getGia).orElse(0);

	            Map<String, Object> map = new HashMap<>();
	            map.put("maCode", vx.getMaCode());
	            map.put("donVi", lo != null ? lo.getDonVi() : "");
	            map.put("namSX", lo != null ? lo.getNgaySanXuat() : null);
	            map.put("gia", gia);
	            return map;
	        }).collect(Collectors.toList());
	    }

	    // Tạo update request từ maCode
	    public QuanLyGiaVacXinUpdateRequest buildUpdateRequest(String maCode) {
	        QuanLyGiaVacXinResponse response = getByMaCode(maCode);
	        return new QuanLyGiaVacXinUpdateRequest(
	                response.getMaCode(),
	                response.getNamSX(),
	                response.getDonVi(),
	                response.getGia()
	        );
	    }


	
	
	@Override
	public Page<QuanLyGiaVacXinResponse> getAllGiaVacXinHienTai(Pageable pageable) {
		// Lấy tất cả vaccine distinct từ lô
		List<VacXinEntity> vacXins = loVacXinService.findAll().stream().map(LoVacXinEntity::getVacXin).distinct()
				.collect(Collectors.toList());

		List<QuanLyGiaVacXinResponse> dtoList = vacXins.stream().map(vx -> {
			// Tìm giá hiện tại
			BangGiaVacXinEntity bangGia = bangGiaVacXinService.findByVacXinIdOrderByHieuLucTuDesc(vx.getId()).stream()
					.filter(bg -> (bg.getHieuLucTu() == null || !bg.getHieuLucTu().isAfter(LocalDate.now()))
							&& (bg.getHieuLucDen() == null || !bg.getHieuLucDen().isBefore(LocalDate.now())))
					.findFirst().orElse(null);

			if (bangGia == null) {
				return null; // ❌ Không có giá thì bỏ qua
			}

			// Lấy thông tin từ 1 lô bất kỳ để hiển thị
			LoVacXinEntity lo = vx.getLoVacXins().stream().findFirst().orElse(null);

			return QuanLyGiaVacXinResponse.builder().maCode(vx.getMaCode()).donVi(lo != null ? lo.getDonVi() : "-")
					.namSX(lo != null ? lo.getNgaySanXuat() : null).gia(bangGia.getGia()) // ✅ Giá từ bảng
																							// bang_gia_vac_xin
					.build();
		}).filter(Objects::nonNull) // ❌ Loại bỏ vaccine không có giá
				.collect(Collectors.toList());

		// Phân trang thủ công
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), dtoList.size());
		List<QuanLyGiaVacXinResponse> content = dtoList.subList(start, end);

		return new PageImpl<>(content, pageable, dtoList.size());
	}
	
	

	@Transactional
	@Override
	public void createGiaVacXin(QuanLyGiaVacXinUpdateRequest request) {
	    LoVacXinEntity loVacXin = loVacXinService.findByVacXinMaCode(request.getMaCode()).orElseGet(() -> {
	        VacXinEntity vacXin = vacXinService.getAllActiveVaccines().stream()
	                .filter(v -> v.getMaCode().equals(request.getMaCode())).findFirst()
	                .orElseThrow(() -> new IllegalArgumentException("Mã vaccine không tồn tại"));

	        LoVacXinEntity newLo = new LoVacXinEntity();
	        newLo.setVacXin(vacXin);
	        newLo.setMaLoCode(request.getMaCode());
	        newLo.setDonVi(request.getDonVi());
	        newLo.setNgaySanXuat(request.getNamSX());
	        return loVacXinService.save(newLo);
	    });

	    // 1. Nếu muốn đánh dấu giá cũ hết hiệu lực
	    bangGiaVacXinService.findByVacXinIdOrderByHieuLucTuDesc(loVacXin.getVacXin().getId())
	        .forEach(bg -> {
	            if (bg.getHieuLucDen() == null) {
	                bg.setHieuLucDen(LocalDate.now().minusDays(1));
	                bangGiaVacXinService.save(bg);
	            }
	        });

	    // 2. Tạo bảng giá mới
	    BangGiaVacXinEntity bangGia = new BangGiaVacXinEntity();
	    mapper.updateBangGiaFromRequest(request, bangGia);
	    bangGia.setVacXin(loVacXin.getVacXin());
	    bangGia.setHieuLucTu(LocalDate.now());
	    bangGia.setHieuLucDen(null); // Giá mới chưa hết hạn
	    bangGiaVacXinService.save(bangGia);
	}


	public void updateGiaVacXin(QuanLyGiaVacXinUpdateRequest request) {
		LoVacXinEntity loVacXin = loVacXinService.findByVacXinMaCode(request.getMaCode())
				.orElseThrow(() -> new IllegalArgumentException("Mã vắc xin không tồn tại"));

		BangGiaVacXinEntity bangGia = bangGiaVacXinService
				.findByVacXinIdOrderByHieuLucTuDesc(loVacXin.getVacXin().getId()).stream().findFirst()
				.orElse(new BangGiaVacXinEntity()); // nếu chưa có thì tạo mới

		mapper.updateLoVacXinFromRequest(request, loVacXin);
		mapper.updateBangGiaFromRequest(request, bangGia);

		bangGia.setVacXin(loVacXin.getVacXin());
		bangGia.setHieuLucTu(LocalDate.now()); // giá mới có hiệu lực từ hôm nay

		loVacXinService.save(loVacXin);
		bangGiaVacXinService.save(bangGia); // giá nhập tay được lưu vào DB
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