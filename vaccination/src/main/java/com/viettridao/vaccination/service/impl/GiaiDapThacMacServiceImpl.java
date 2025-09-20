package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.supportemployee.GiaiDapThacMacRequest;
import com.viettridao.vaccination.dto.response.supportemployee.GiaiDapThacMacResponse;
import com.viettridao.vaccination.mapper.GiaiDapThacMacMapper;
import com.viettridao.vaccination.model.PhanHoiEntity;
import com.viettridao.vaccination.repository.PhanHoiRepository;
import com.viettridao.vaccination.service.GiaiDapThacMacService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GiaiDapThacMacServiceImpl implements GiaiDapThacMacService {

	private final PhanHoiRepository phanHoiRepository;
	private final GiaiDapThacMacMapper mapper;
	private final JavaMailSender mailSender;

	@Override
	public List<GiaiDapThacMacResponse> getAll() {
		return phanHoiRepository.findAll().stream().filter(p -> p.getLoaiPhanHoi() != PhanHoiEntity.LoaiPhanHoi.CAU_HOI) // bỏ
																															// CAU_HOI
				.map(mapper::toResponse).toList();
	}

	@Override
	public GiaiDapThacMacResponse getByMaPh(String maPh) {
		PhanHoiEntity entity = phanHoiRepository.findById(maPh)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy phản hồi với mã: " + maPh));
		return mapper.toResponse(entity);
	}

	@Override
	@Transactional
	public void traLoi(GiaiDapThacMacRequest request) {
		if (request.getMaPh() == null || request.getMaPh().isBlank()) {
			throw new IllegalArgumentException("Mã phản hồi không hợp lệ hoặc đã hết phiên.");
		}

		PhanHoiEntity entity = phanHoiRepository.findById(request.getMaPh())
				.orElseThrow(() -> new RuntimeException("Không tìm thấy phản hồi với mã: " + request.getMaPh()));

		// Không ghi đè câu hỏi gốc
		entity.setTrangThai(PhanHoiEntity.TrangThai.DA_XU_LY);
		phanHoiRepository.save(entity);

		// Gửi email trả lời
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(request.getEmailBenhNhan());
			message.setSubject("Giải đáp thắc mắc từ Trung tâm tiêm chủng");
			message.setText("Câu hỏi của bạn: \n" + entity.getTieuDe() + "\n\nNội dung: \n" + entity.getNoiDung()
					+ "\n\nTrả lời: \n" + request.getTraLoi());
			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Gửi email thất bại: " + e.getMessage(), e);
		}
	}
}
