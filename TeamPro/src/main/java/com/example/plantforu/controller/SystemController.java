package com.example.plantforu.controller;

import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class SystemController {
	// 멤버 로그인 화면 보이기
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/login")
	public String memberLogin() {
		return "system/member_login";
	}
	
	// 관리자 로그인 화면 보이기
	@GetMapping("/admin/login")
	@PreAuthorize("isAnonymous()")
	public String adminLogin() {
		return "system/admin_login";
	}
	
	// 로그인했지만 권한이 틀리다면 -> accessDeniedHandler에 의해 21라인으로 이동하게 된다
	@GetMapping("/error/e403")
	public String e403() {
		return "system/e403";
	}
}
