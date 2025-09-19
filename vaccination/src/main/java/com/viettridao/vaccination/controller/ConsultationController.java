package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.viettridao.vaccination.dto.response.supportemployee.CauHoiTiemChungResponse;
import com.viettridao.vaccination.service.CauHoiTiemChungService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/consultation")
@RequiredArgsConstructor
public class ConsultationController {

	private final CauHoiTiemChungService cauHoiService;

	@GetMapping
	public String showConsultationForm(Model model) {
		model.addAttribute("pageTitle", "Tư vấn tiêm chủng");
		model.addAttribute("cauHois", cauHoiService.getAllCauHoi());
		return "supportEmployee/consultation-form";
	}

	@GetMapping("/{maCauHoi}")
	@ResponseBody
	public CauHoiTiemChungResponse getCauHoiDetail(@PathVariable String maCauHoi) {
		return cauHoiService.getCauHoiById(maCauHoi);
	}

	@PostMapping("/create")
	public String createCauHoi(@RequestParam String tieuDe, @RequestParam String noiDung) {
		cauHoiService.createCauHoi(tieuDe, noiDung);
		return "redirect:/consultation";
	}
	
	@GetMapping("/create-form")
	public String showCreateForm() {
	    return "supportEmployee/create-consultation-form";
	}

	@GetMapping("/edit-form/{maCauHoi}")
	public String showEditForm(@PathVariable String maCauHoi, Model model) {
	    model.addAttribute("cauHoi", cauHoiService.getCauHoiById(maCauHoi));
	    return "supportEmployee/edit-consultation-form";
	}

	@PostMapping("/update/{maCauHoi}")
	public String updateCauHoi(@PathVariable String maCauHoi, @RequestParam String tieuDe,
			@RequestParam String noiDung) {
		cauHoiService.updateCauHoi(maCauHoi, tieuDe, noiDung);
		return "redirect:/consultation";
	}

	@PostMapping("/delete/{maCauHoi}")
	public String deleteCauHoi(@PathVariable String maCauHoi) {
		cauHoiService.deleteCauHoi(maCauHoi);
		return "redirect:/consultation";
	}
}
