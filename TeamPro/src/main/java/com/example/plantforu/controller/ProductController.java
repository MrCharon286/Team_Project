package com.example.plantforu.controller;

import java.io.*;
import java.nio.file.*;
import java.security.*;

import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

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
	/*@GetMapping("/product/list")
	public void productList() {
	}
	
	// 게시물 보기
	@GetMapping("/product/read")
	public void productDetail() {
	}*/
	
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
	
	// 상품 설명 보기
	@GetMapping(path="/products/detail", produces=MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> showDetail(String detailname) throws IOException {
		
		// ck로 이미지를 업로드하면 일단 temp 폴더에 저장. 상품을 등록하는 순간에 temp의 이미지를 product로 이동
		// 즉 이미지를 출력하는 이곳에서는 봤을 때 이미지가 temp에 있을 수도 product에 있을 수도 있다
		// product와 temp 폴더에서 차례대로 이미지를 찾는다
		File file = new File(PlantforuConstant.PRODUCT_FOLDER + detailname);
		if(file.exists()==false)
			file = new File(PlantforuConstant.TEMP_FOLDER + detailname);
		if(file.exists()==false)
			return null;
		
		// 위에서 찾은 이미지를 출력
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(PlantforuUtil.getMediaType(detailname));
		headers.add("Content-Disposition", "inline;filename="  + detailname);
		try {
			return ResponseEntity.ok().headers(headers).body(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// ck 이미지 업로드
	@PostMapping(value="/product/image", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CKResponse> ckImageUpload(MultipartFile upload) {
		return ResponseEntity.ok(service.ckImageUpload(upload));
	}
	
	// ck 이미지(상품설명) 업로드
	@PostMapping(value="/product/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CKResponse> ckDetailUpload(MultipartFile upload) {
		return ResponseEntity.ok(service.ckImageUpload(upload));
	}
	
	@PostMapping(path="/product/add", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> productInsert(@Valid ProductDto.Create dto, BindingResult bindingResult) throws BindException {
		PlantforuUtil.bindingResultCheck(bindingResult);
		return ResponseEntity.ok(service.add(dto));
	}
	
	// 상품 목록
	@GetMapping(path="/products/list", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto.Page> productList(@Valid ProductDto.ForList dto, BindingResult bindingResult) throws BindException {
		PlantforuUtil.bindingResultCheck(bindingResult);
		return ResponseEntity.ok(service.list(dto));
	}	
	
	// 상품 정보
	@GetMapping(path="/product/read/{pno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> productDetail(@PathVariable Integer pno) {
		return ResponseEntity.ok(service.read(pno));
	}
	
	// 상품 정보 수정
	@PutMapping(path="/product/read/{pno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> productUpdate(@Valid ProductDto.Update dto, BindingResult bindingResult) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		return ResponseEntity.ok(service.update(dto));
	}
	
	// 상품 삭제
	@DeleteMapping(path="/product/read/{pno}")
	public ResponseEntity<Product> productDelete(@PathVariable Integer pno) throws BindException {
		return ResponseEntity.ok(service.delete(pno));
	}
}
