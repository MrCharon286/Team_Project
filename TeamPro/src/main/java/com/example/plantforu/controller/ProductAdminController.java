package com.example.plantforu.controller;

import javax.validation.*;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.service.*;
import com.example.plantforu.util.*;
import com.fasterxml.jackson.annotation.*;

import lombok.*;

@RequiredArgsConstructor
@Controller
public class ProductAdminController {
	private final ProductAdminService service;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/admin/product/list")
	public String list() {
		return "system/product_list";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/admin/product/add")
	public String add() {
		return "system/product_add";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/admin/product/read")
	public String read() {
		return "system/product_read";
	}
	/*
	// ProductController 에도 ck이미지 업로드 존재...
	// ck 이미지 업로드
	@PostMapping(value="/product/image", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CKResponse> ckImageUpload(MultipartFile upload) {
		return ResponseEntity.ok(service.ckImageUpload(upload));
	}
	*/
	// 상품 추가
	@PostMapping(path="/admin/products/new", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> add(@Valid ProductDto.Create dto, BindingResult bindingResult) throws BindException {
		PlantforuUtil.bindingResultCheck(bindingResult);
		return ResponseEntity.ok(service.add(dto));
	}
	
	// 상품 정보
	@JsonView(ProductDto.ReadForAdmin.class)
	@GetMapping(path="/admin/products/{pno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> read(@PathVariable Integer pno) {
		return ResponseEntity.ok(service.read(pno));
	}
}
