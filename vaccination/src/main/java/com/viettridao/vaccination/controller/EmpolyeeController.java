package com.viettridao.vaccination.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.viettridao.vaccination.dto.request.employee.KeDonRequest;
import com.viettridao.vaccination.dto.request.employee.KetQuaTiemRequest;
import com.viettridao.vaccination.dto.request.employee.UpdateBenhNhanRequest;
import com.viettridao.vaccination.dto.response.employee.HoSoBenhAnResponse;
import com.viettridao.vaccination.dto.response.employee.KeDonResponse;
import com.viettridao.vaccination.dto.response.employee.KetQuaTiemResponse;
import com.viettridao.vaccination.dto.response.employee.UpdateBenhNhanResponse;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.service.BenhNhanService;
import com.viettridao.vaccination.service.HoSoBenhAnService;
import com.viettridao.vaccination.service.KeDonService;
import com.viettridao.vaccination.service.KetQuaTiemService;
import com.viettridao.vaccination.service.VacXinService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmpolyeeController {

	private final HoSoBenhAnService hoSoBenhAnService;
	private final BenhNhanService benhNhanService;
	private final KeDonService keDonService;
	private final KetQuaTiemService ketQuaTiemService;
	private final VacXinService vacXinService; // giờ sẽ resolve được

	@GetMapping("/view")
	public String showEmployeeView(Model model) {
		model.addAttribute("tab", "view");
		return "employee/view";
	}

	@PostMapping("/view")
	public String searchHoSoBenhAn(@RequestParam("maBenhNhan") String maBenhNhan, Model model) {
		try {
			HoSoBenhAnResponse hoSo = hoSoBenhAnService.getHoSoBenhAnById(maBenhNhan);
			model.addAttribute("hoSo", hoSo);
			model.addAttribute("success", "Tìm kiếm thành công");
		} catch (RuntimeException ex) {
			model.addAttribute("error", ex.getMessage());
		}
		model.addAttribute("tab", "view");
		return "employee/view";
	}

	@GetMapping("/update")
	public String showUpdateForm(@RequestParam(required = false) String maBenhNhan, Model model) {
		List<UpdateBenhNhanResponse> allPatients = benhNhanService.getAllBenhNhan();
		model.addAttribute("allPatients", allPatients);

		UpdateBenhNhanRequest request = new UpdateBenhNhanRequest();

		if (maBenhNhan != null && !maBenhNhan.isEmpty()) {
			UpdateBenhNhanResponse response = benhNhanService.getBenhNhanById(maBenhNhan);
			if (response != null) {
				request = UpdateBenhNhanRequest.builder().maBenhNhan(response.getMaBenhNhan())
						.hoTen(response.getHoTen()).gioiTinh(response.getGioiTinh()).tuoi(response.getTuoi())
						.tenNguoiGiamHo(response.getTenNguoiGiamHo()).diaChi(response.getDiaChi())
						.soDienThoai(response.getSoDienThoai()).build();
			}
		}

		model.addAttribute("benhNhan", request);
		return "employee/update";
	}

	// Xử lý POST cập nhật
	@PostMapping("/update")
	public String updateBenhNhan(@ModelAttribute("benhNhan") UpdateBenhNhanRequest request, Model model) {
		try {
			UpdateBenhNhanResponse response = benhNhanService.updateBenhNhan(request);

			// Bind dữ liệu mới lên form
			model.addAttribute("benhNhan",
					UpdateBenhNhanRequest.builder().maBenhNhan(response.getMaBenhNhan()).hoTen(response.getHoTen())
							.gioiTinh(response.getGioiTinh()).tuoi(response.getTuoi())
							.tenNguoiGiamHo(response.getTenNguoiGiamHo()).diaChi(response.getDiaChi())
							.soDienThoai(response.getSoDienThoai()).build());

			model.addAttribute("success", "Cập nhật thành công");
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi: " + e.getMessage());
		}

		// Luôn load lại danh sách bệnh nhân cho dropdown
		model.addAttribute("allPatients", benhNhanService.getAllBenhNhan());

		return "employee/update";
	}

	@GetMapping("/prescripe")
	public String prescripeEmpoyee(Model model) {
		model.addAttribute("tab", "prescripe");
		model.addAttribute("keDonRequest", new KeDonRequest());

		model.addAttribute("benhNhanList", keDonService.getAllBenhNhan());
		model.addAttribute("vacXinList", keDonService.getAllVacXin());

		return "employee/prescripe";
	}

	@PostMapping("/prescripe")
	public String keDon(@ModelAttribute("keDonRequest") KeDonRequest request, Model model) {
		try {
			KeDonResponse response = keDonService.keDon(request, "userLoginFake");
			model.addAttribute("success", "Kê đơn thành công!");
		} catch (Exception e) {
			model.addAttribute("error", "Có lỗi khi kê đơn: " + e.getMessage());
		}

		model.addAttribute("benhNhanList", keDonService.getAllBenhNhan());
		model.addAttribute("vacXinList", keDonService.getAllVacXin());
		model.addAttribute("tab", "prescripe");

		return "employee/prescripe";
	}

	@GetMapping("/list")
	public String listKetQuaTiem(Model model) {
		List<KetQuaTiemResponse> ketQuaTiems = ketQuaTiemService.getAllKetQuaTiem();
		model.addAttribute("ketQuaTiems", ketQuaTiems);
		return "employee/immunization-result-list";
	}
	
	@GetMapping("/add")
	public String showAddKetQuaForm(Model model) {
	    KetQuaTiemRequest request = new KetQuaTiemRequest();

	    model.addAttribute("ketQuaTiemRequest", request);

	    model.addAttribute("benhNhanList", ketQuaTiemService.getAllKetQuaTiem()
	            .stream().map(KetQuaTiemResponse::getTenBenhNhan).distinct().toList());
	    model.addAttribute("vacXinList", vacXinService.getAllVaccines()
	            .stream().map(VacXinEntity::getTen).toList());
	    model.addAttribute("nguoiThucHienList", ketQuaTiemService.getAllKetQuaTiem()
	            .stream().map(KetQuaTiemResponse::getNguoiThucHien).distinct().toList());

	    return "employee/immunization-result-add";
	}
	
	@GetMapping("/benh-nhan-info")
	@ResponseBody
	public KetQuaTiemResponse getBenhNhanInfo(@RequestParam String tenBenhNhan) {
	    // Lấy thông tin đầy đủ từ KetQuaTiemService nếu đã tiêm, hoặc từ BenhNhanService nếu chưa
	    KetQuaTiemResponse response = ketQuaTiemService.getKetQuaTiemByTenBenhNhan(tenBenhNhan);
	    if(response == null) {
	        // Nếu bệnh nhân chưa có kết quả tiêm, chỉ trả tên bệnh nhân
	        response = new KetQuaTiemResponse();
	        response.setTenBenhNhan(tenBenhNhan);
	    }
	    return response;
	}


	@PostMapping("/add")
	public String addKetQua(@ModelAttribute("ketQuaTiemRequest") KetQuaTiemRequest request, Model model) {
	    try {
	        ketQuaTiemService.createKetQuaTiem(request);
	        model.addAttribute("success", "Thêm kết quả tiêm thành công!");
	    } catch (Exception e) {
	        model.addAttribute("error", "Có lỗi khi thêm kết quả tiêm: " + e.getMessage());
	    }

	    // Load lại list để hiển thị
	    List<KetQuaTiemResponse> ketQuaTiems = ketQuaTiemService.getAllKetQuaTiem();
	    model.addAttribute("ketQuaTiems", ketQuaTiems);

	    model.addAttribute("tab", "addKetQua");
	    return "employee/immunization-result-list";
	}

}
