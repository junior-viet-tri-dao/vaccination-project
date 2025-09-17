package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.supportemployee.NhacNhoTiemRequest;
import com.viettridao.vaccination.dto.response.supportemployee.NhacNhoTiemResponse;
import com.viettridao.vaccination.mapper.NhacNhoTiemMapper;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.repository.BangGiaVacXinRepository;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.KetQuaTiemRepository;
import com.viettridao.vaccination.repository.LichTiemRepository;
import com.viettridao.vaccination.service.NhacNhoTiemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NhacNhoTiemServiceImpl implements NhacNhoTiemService {

	private final KetQuaTiemRepository ketQuaTiemRepository;
	private final LichTiemRepository lichTiemRepository;
	private final BangGiaVacXinRepository bangGiaRepository;
	private final BenhNhanRepository benhNhanRepository;
	private final NhacNhoTiemMapper mapper;
	private final JavaMailSender mailSender;

	@Override
	public List<NhacNhoTiemResponse> getLichSuVaDuKien(String maBenhNhan) {
		BenhNhanEntity benhNhan = benhNhanRepository.findById(maBenhNhan).orElse(null);
		if (benhNhan == null)
			return List.of();

		List<KetQuaTiemEntity> ketQuaList = ketQuaTiemRepository.findAllByBenhNhan_IdAndIsDeletedFalse(maBenhNhan);

		return ketQuaList.stream().map(ketQua -> {
			LichTiemEntity lichTiemDaTiem = ketQua.getLichTiem(); // Lấy lịch đã tiêm
			LichTiemEntity lichTiemDuKien = null;
			BangGiaVacXinEntity bangGia = null;

			if (lichTiemDaTiem != null && lichTiemDaTiem.getVacXin() != null) {
				// Lịch dự kiến của cùng loại vắc xin
				lichTiemDuKien = lichTiemRepository.findTopByVacXinOrderByNgayGioAsc(lichTiemDaTiem.getVacXin());

				// Lấy giá mới nhất
				bangGia = bangGiaRepository.findTopByVacXin_IdOrderByHieuLucTuDesc(lichTiemDaTiem.getVacXin().getId());

			}

			return mapper.toResponse(ketQua, lichTiemDaTiem, lichTiemDuKien, bangGia, benhNhan);
		}).collect(Collectors.toList());
	}

	@Override
	public List<NhacNhoTiemResponse> getAllReminders() {
		List<KetQuaTiemEntity> ketQuaList = ketQuaTiemRepository.findAllByIsDeletedFalse();

		return ketQuaList.stream().map(ketQua -> {
			LichTiemEntity lichTiemDaTiem = ketQua.getLichTiem();
			LichTiemEntity lichTiemDuKien = null;
			BangGiaVacXinEntity bangGia = null;
			BenhNhanEntity benhNhan = ketQua.getBenhNhan();

			if (lichTiemDaTiem != null && lichTiemDaTiem.getVacXin() != null) {
				// Lấy lịch dự kiến cùng loại vắc xin
				lichTiemDuKien = lichTiemRepository.findTopByVacXinOrderByNgayGioAsc(lichTiemDaTiem.getVacXin());

				// Lấy giá mới nhất
				bangGia = bangGiaRepository.findTopByVacXinOrderByHieuLucTuDesc(lichTiemDaTiem.getVacXin());
			}

			return mapper.toResponse(ketQua, lichTiemDaTiem, lichTiemDuKien, bangGia, benhNhan);
		}).collect(Collectors.toList());
	}

	@Override
	public void sendEmail(NhacNhoTiemRequest request) {
		if (request.getEmail() == null || request.getEmail().isEmpty()) {
			throw new RuntimeException("Email nhận trống");
		}

		try {
			// Tạo nội dung email
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(request.getEmail());

			StringBuilder content = new StringBuilder("Lịch tiêm chủng của bạn:\n\n");
			int stt = 1;
			for (NhacNhoTiemRequest.ThongTinTiemDto dto : request.getLichTiem()) {
				content.append(stt++).append(". Ngày tiêm: ").append(dto.getNgayTiem()).append(", Vắc xin đã tiêm: ")
						.append(dto.getLoaiVacXinDaTiem()).append(", Ngày dự kiến: ").append(dto.getNgayDuKien())
						.append(", Vắc xin dự kiến: ").append(dto.getLoaiVacXinDuKien()).append(", Giá: ")
						.append(dto.getGia()).append("\n");
			}

			message.setSubject("Nhắc nhở tiêm chủng");
			message.setText(content.toString());

			// Gửi email
			mailSender.send(message);
			System.out.println("Email đã gửi tới: " + request.getEmail());

			// Không cập nhật daGui nữa

		} catch (Exception e) {
			e.printStackTrace(); // In ra lý do lỗi
			throw new RuntimeException("Lỗi gởi email", e);
		}
	}
}