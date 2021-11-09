package com.example.plantforu.controller;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class SystemSampleController {
	@GetMapping("/test1")
	public ResponseEntity<?> test1() {
		return ResponseEntity.ok("누구나 접근 가능");
	}
	
	@GetMapping("/admin/test2")
	public ResponseEntity<?> test2() {
		return ResponseEntity.ok("경로를 이용해 관리자만 접근 가능");
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/test3")
	public ResponseEntity<?> test3() {
		return ResponseEntity.ok("@Secured를 이용해 사용자만 접근 가능");
	}
}
