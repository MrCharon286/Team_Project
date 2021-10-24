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
public class ProductSellerController {
	private final ProductSellerService service;
	
	@Secured("ROLE_SELLER")
	@GetMapping("/seller/product/list")
	public String productListSeller() {
		return "system/product_list";
	}
	
	@Secured("ROLE_SELLER")
	@GetMapping("/product/add")
	public String productInsert() {
		return "system/product_add";
	}
	
	@Secured("ROLE_SELLER")
	@GetMapping("/seller/product/add")	
	public String productDetail() {
		return "system/product_read";
	}
	
	// ck 이미지 업로드
	@PostMapping(value="/product/image", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CKResponse> ckImageUpload(MultipartFile upload) {
		return ResponseEntity.ok(service.ckImageUpload(upload));
	}
	
	@PostMapping(path="/seller/products/new", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> productInsert(@Valid ProductDto.Create dto, BindingResult bindingResult) throws BindException {
		PlantforuUtil.bindingResultCheck(bindingResult);
		return ResponseEntity.ok(service.add(dto));
	}
	
	// 상품 정보
	@GetMapping(path="/seller/products/{pno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductImage> productDetail(@PathVariable Integer pno) {
		return ResponseEntity.ok(service.read(pno));
	}
}
