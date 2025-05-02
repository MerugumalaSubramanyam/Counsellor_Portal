package com.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ashokit.dto.CounsellorDto;
import com.ashokit.dto.DashboardDto;
import com.ashokit.services.CounsellorService;
import com.ashokit.services.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	CounsellorService counsellorService;

	@Autowired
	EnquiryService enqService;

	@GetMapping("/")
	public String index(Model model) {
		CounsellorDto counsellorDto = new CounsellorDto();
		model.addAttribute("counsellor", counsellorDto);
		return "index";
	}

	@PostMapping("/login")
	public String handleLogin(CounsellorDto counsellor, HttpServletRequest request, Model model) {
		CounsellorDto dto = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
		if (dto == null) {
			model.addAttribute("emsg", "Invalid Credentials");

			CounsellorDto counsellorDto = new CounsellorDto();
			model.addAttribute("counsellor", counsellorDto);

			return "index";
		} else {
			HttpSession session = request.getSession(true);
			session.setAttribute("COUNSELLOR_ID", dto.getCounsellorId());

			return "redirect:/dashboard";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		session.invalidate();

		return "redirect:/";
	}

	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");
		DashboardDto dashboardInfo = enqService.getDashboardInfo(cid);
		model.addAttribute("dashboardInfo", dashboardInfo);
		return "dashboardReportView";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		CounsellorDto counsellorDto = new CounsellorDto();
		model.addAttribute("counsellor", counsellorDto);

		return "registerView";
	}

	@PostMapping("/register")
	public String handleRegistration(@ModelAttribute("counsellor") CounsellorDto counsellor, Model model) {
		boolean status = counsellorService.isEmailUniqe(counsellor.getEmail());
		if (status) {
			boolean register = counsellorService.register(counsellor);
			if (register) {
				model.addAttribute("smsg", "Registration Success");
			} else {
				model.addAttribute("emsg", "Registration Failed");
			}
		} else {
			model.addAttribute("emsg", "Duplicate Email Found");
		}

		return "registerView";
	}
}
