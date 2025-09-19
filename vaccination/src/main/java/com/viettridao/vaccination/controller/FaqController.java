package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viettridao.vaccination.dto.request.supportemployee.GiaiDapThacMacRequest;
import com.viettridao.vaccination.dto.response.supportemployee.GiaiDapThacMacResponse;
import com.viettridao.vaccination.service.GiaiDapThacMacService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FaqController {

	private final GiaiDapThacMacService giaiDapThacMacService;

	// Trang danh sách câu hỏi
	@GetMapping
	public String listFaq(Model model) {
		var danhSach = giaiDapThacMacService.getAll();
		model.addAttribute("pageTitle", "Danh sách thắc mắc");
		model.addAttribute("faqList", danhSach);
		return "supportEmployee/faq-list";
	}

	// Form trả lời 1 phản hồi cụ thể
	@GetMapping("/{maPh}")
	public String showFaqForm(@PathVariable String maPh, Model model) {
		GiaiDapThacMacResponse faqResponse = giaiDapThacMacService.getByMaPh(maPh);

		GiaiDapThacMacRequest faqRequest = new GiaiDapThacMacRequest();
		faqRequest.setMaPh(maPh);
		faqRequest.setEmailBenhNhan(faqResponse.getEmailBenhNhan());

		model.addAttribute("pageTitle", "Giải đáp thắc mắc");
		model.addAttribute("faqResponse", faqResponse);
		model.addAttribute("faqRequest", faqRequest);
		return "supportEmployee/faq-form";
	}

	@PostMapping("/tra-loi")
	public String traLoi(@Valid @ModelAttribute("faqRequest") GiaiDapThacMacRequest request, 
	                     BindingResult result,
	                     Model model, 
	                     RedirectAttributes redirectAttrs) {

	    if (result.hasErrors()) {
	        if (request.getMaPh() != null && !request.getMaPh().isBlank()) {
	            model.addAttribute("faqResponse", giaiDapThacMacService.getByMaPh(request.getMaPh()));
	        }
	        model.addAttribute("pageTitle", "Giải đáp thắc mắc");
	        return "supportEmployee/faq-form";
	    }

	    if (request.getMaPh() == null || request.getMaPh().isBlank()) {
	        redirectAttrs.addFlashAttribute("errorMessage",
	                "Mã phản hồi không hợp lệ hoặc đã hết phiên. Vui lòng chọn lại từ danh sách.");
	        return "redirect:/faq";
	    }

	    try {
	        giaiDapThacMacService.traLoi(request);
	        redirectAttrs.addFlashAttribute("successMessage", "Trả lời thành công, email đã được gửi!");
	        // 👉 Sau khi gửi thì quay về danh sách
	        return "redirect:/faq";
	    } catch (RuntimeException ex) {
	        redirectAttrs.addFlashAttribute("errorMessage", ex.getMessage());
	        return "redirect:/faq";
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttrs.addFlashAttribute("errorMessage", "Đã có lỗi xảy ra: " + e.getMessage());
	        return "redirect:/faq";
	    }
	}
}