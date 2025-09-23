package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiSauTiemRequest;
import com.viettridao.vaccination.dto.response.normalUser.PhanHoiSauTiemResponse;
import com.viettridao.vaccination.mapper.PhanHoiSauTiemMapper;
import com.viettridao.vaccination.model.BaoCaoPhanUngEntity;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.BaoCaoPhanUngRepository;
import com.viettridao.vaccination.repository.KetQuaTiemRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.service.PhanHoiSauTiemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhanHoiSauTiemServiceImpl implements PhanHoiSauTiemService {

	private final BaoCaoPhanUngRepository baoCaoPhanUngRepository;
	private final KetQuaTiemRepository ketQuaTiemRepository;
	private final TaiKhoanRepository taiKhoanRepository;
	private final PhanHoiSauTiemMapper phanHoiSauTiemMapper;

	@Override
	public List<PhanHoiSauTiemResponse> getKetQuaTiemCanPhanHoiByCurrentUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		TaiKhoanEntity taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
		BenhNhanEntity benhNhan = taiKhoan.getBenhNhan();

		List<KetQuaTiemEntity> ketQuaList = ketQuaTiemRepository.findAllByBenhNhanAndTinhTrang(benhNhan,
				KetQuaTiemEntity.TinhTrangTinhTrang.HOAN_THANH);

		return ketQuaList.stream()
				.filter(kq -> !baoCaoPhanUngRepository.existsByKetQuaTiemIdAndTrangThaiPhanHoi(kq.getId(),
						BaoCaoPhanUngEntity.TrangThaiPhanHoi.DA_PHAN_HOI))
				.map(phanHoiSauTiemMapper::toResponse).toList();

	}

	@Override
	public PhanHoiSauTiemResponse getByKetQuaTiemId(String ketQuaTiemId) {
		// Giả sử bạn lấy từ KetQuaTiem hoặc từ BaoCaoPhanUng
		KetQuaTiemEntity ketQuaTiem = ketQuaTiemRepository.findById(ketQuaTiemId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả tiêm"));
		return phanHoiSauTiemMapper.toResponse(ketQuaTiem);
	}

	@Override
	public void taoPhanHoiSauTiem(PhanHoiSauTiemRequest request) {
		// 1. Lấy user đang đăng nhập
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		TaiKhoanEntity taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

		BenhNhanEntity benhNhan = taiKhoan.getBenhNhan();

		// 2. Lấy thông tin kết quả tiêm
		KetQuaTiemEntity ketQuaTiem = ketQuaTiemRepository.findById(request.getKetQuaTiemId())
				.orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả tiêm"));

		// 3. Map từ request -> entity
		BaoCaoPhanUngEntity entity = phanHoiSauTiemMapper.toEntity(request);
		entity.setBenhNhan(benhNhan);
		entity.setVacXin(ketQuaTiem.getLichTiem().getVacXin());
		entity.setKetQuaTiem(ketQuaTiem);
		entity.setTaoBoi(taiKhoan);
		entity.setTrangThaiPhanHoi(BaoCaoPhanUngEntity.TrangThaiPhanHoi.DA_PHAN_HOI);
		entity.setKenhBaoCao(BaoCaoPhanUngEntity.KenhBaoCao.BENH_NHAN);

		// 4. Lưu vào DB
		baoCaoPhanUngRepository.save(entity);
	}
}