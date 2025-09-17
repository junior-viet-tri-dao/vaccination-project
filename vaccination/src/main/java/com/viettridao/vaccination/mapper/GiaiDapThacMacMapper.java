package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.viettridao.vaccination.dto.request.supportemployee.GiaiDapThacMacRequest;
import com.viettridao.vaccination.dto.response.supportemployee.GiaiDapThacMacResponse;
import com.viettridao.vaccination.model.PhanHoiEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GiaiDapThacMacMapper {

	// Entity -> Response
	@Mapping(source = "id", target = "maPh") // id của phản hồi
	@Mapping(source = "tieuDe", target = "cauHoi") // câu hỏi
	@Mapping(source = "noiDung", target = "traLoi") // câu trả lời
	@Mapping(source = "benhNhan.email", target = "emailBenhNhan")
	@Mapping(source = "trangThai", target = "trangThai")
	GiaiDapThacMacResponse toResponse(PhanHoiEntity entity);

	// Request -> Entity (cập nhật câu trả lời)
	@Mapping(source = "maPh", target = "id") // maPh request gắn vào id
	@Mapping(source = "traLoi", target = "noiDung") // gán trả lời vào noiDung
	// emailBenhNhan không map vào entity (nó đến từ BenhNhan)
	PhanHoiEntity toEntity(GiaiDapThacMacRequest request);

	// Request -> Response (trả ngay cho FE sau khi gửi trả lời)
	@Mapping(source = "maPh", target = "maPh")
	@Mapping(source = "traLoi", target = "traLoi")
	@Mapping(source = "emailBenhNhan", target = "emailBenhNhan")
	GiaiDapThacMacResponse fromRequest(GiaiDapThacMacRequest request);
}
