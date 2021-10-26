package com.example.plantforu.controller;

import java.io.*;
import java.nio.file.*;

import javax.validation.*;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.service.*;
import com.example.plantforu.util.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import lombok.*;

@Controller
@RequiredArgsConstructor
public class ProductController {
	private final ProductService service;
	
	// 게시물 목록
	@GetMapping("/product/list")
	public void productListRecent() {
	}
	
	// 게시물 보기
	@GetMapping("/product/read")
	public void productDetail() {
	}
	
	// 상품 이미지 보기
	@GetMapping(path="/products/image", produces=MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> showImage(String imagename) throws IOException {
		
		// ck로 이미지를 업로드하면 일단 temp 폴더에 저장. 상품을 등록하는 순간에 temp의 이미지를 product로 이동
		// 즉 이미지를 출력하는 이곳에서는 봤을 때 이미지가 temp에 있을 수도 product에 있을 수도 있다
		// product와 temp 폴더에서 차례대로 이미지를 찾는다
		File file = new File(PlantforuConstant.PRODUCT_FOLDER + imagename);
		if(file.exists()==false)
			file = new File(PlantforuConstant.TEMP_FOLDER + imagename);
		if(file.exists()==false)
			return null;
		
		// 위에서 찾은 이미지를 출력
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(PlantforuUtil.getMediaType(imagename));
		headers.add("Content-Disposition", "inline;filename="  + imagename);
		try {
			return ResponseEntity.ok().headers(headers).body(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 상품 목록
	@GetMapping(path="/products/all", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto.Page> productListRecent(@Valid ProductDto.ForList dto, BindingResult bindingResult) throws BindException {
		PlantforuUtil.bindingResultCheck(bindingResult);
		return ResponseEntity.ok(service.list(dto));
	}
	
	// 상품 정보
	@JsonView(ProductDto.ReadForMember.class)
	@GetMapping(path="/products/{pno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductImage> productDetail(@PathVariable Integer pno) throws JsonProcessingException {
		return ResponseEntity.ok(service.read(pno));
	}
	
	// 재고 확인
	@GetMapping(path="/products/stock", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> checkStock(Integer pno, Integer count) {
		return ResponseEntity.ok(service.checkStock(pno, count));
	}
}
