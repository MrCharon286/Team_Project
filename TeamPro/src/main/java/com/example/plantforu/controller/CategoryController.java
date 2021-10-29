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
	
	@GetMapping(path="/categories/all", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readCategoryAll(@RequestParam(required=false) Integer ctgno) {
		return ResponseEntity.ok(service.readCategoryAll());
	}
}
