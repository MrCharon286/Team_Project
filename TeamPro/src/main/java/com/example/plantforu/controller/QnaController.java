package com.example.plantforu.controller;

import java.net.*;
import java.security.*;
import java.util.*;
import java.util.List;

import javax.servlet.http.*;
import javax.validation.*;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.security.web.csrf.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.util.*;
import org.springframework.validation.*;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.controller.dto.ProductDto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.service.*;

import lombok.*;

//@Secured("ROLE_USER")
@RequiredArgsConstructor
@Controller
public class QnaController {
	private final QnaService service;
	private final RestTemplate restTemplate;
	
	@GetMapping("/qna/list")
	public void memberQnaList(Principal principal) {
	}
	
	@GetMapping("/qna/write_qna")
	public String memberQnaWriteRead(Integer pno, Model model) {
		// MultivalueMap을 만들고 요청 파라미터를 추가 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("pno", pno+"");

		// get 방식 요청이므로 params 맵을 그대로 전송
		URI uri = UriComponentsBuilder.fromOriginHeader("http://localhost:8081").path("/products/{pno}").queryParams(params).build().toUri();
		ResponseEntity<ProductDto.QnaInfo> result = restTemplate.getForEntity(uri.toString(), QnaInfo.class);
		System.out.println(result.getBody());
		model.addAttribute("product", result.getBody());
		return "qna/write_qna";
	}
	
	@PostMapping("/qnas")
	public ResponseEntity<Void> memberQnaWrite(@Valid QnaDto.Write dto, BindingResult bindingResult, Principal principal, HttpServletRequest req) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		
		service.writeQna(dto, principal.getName());
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("pno", dto.getPno()+"");
		
		URI uri = UriComponentsBuilder.fromOriginHeader("http://localhost:8081").path("/products/update").queryParams(params).build().toUri();
		ResponseEntity<Integer> result = restTemplate.getForEntity(uri.toString(), Integer.class);
		
		return ResponseEntity.ok(null);
	}
}

