package com.example.plantforu.controller;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.plantforu.service.*;

import lombok.*;

@RequiredArgsConstructor
@Controller
public class CategoryController {
	private final CategoryService service;

	// 대, 중 분류를 선택하면 자식 중, 소 분류를 가지고 오는 메서드
	// 상품 추가는 관리자만 가능하므로 /admin/** 경로
	@GetMapping(path="/admin/categories", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> read(@RequestParam(required=false) Integer ctgno) {
		return ResponseEntity.ok(service.readCategory(ctgno));
	}
	
	@GetMapping(path="/categories/all", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readCategoryAll(@RequestParam(required=false) Integer ctgno) {
		return ResponseEntity.ok(service.readCategoryAll());
	}
}
