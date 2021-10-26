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
import com.example.plantforu.controller.dto.OrderDto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.service.*;

import lombok.*;

//@Secured("ROLE_USER")
@RequiredArgsConstructor
@Controller
public class ReviewController {
	private final ReviewService service;
	private final RestTemplate restTemplate;
	
	/*
	@GetMapping("/review/available_list")
	public void availableList(Principal principal) {
	}
	*/
	
	@GetMapping("/review/list")
	public void productReviewList(Principal principal) {
	}
	
	@GetMapping("/review/write_review")
	public String showReviewView(Integer orderNo, Integer orderItemNo, Model model) {
		// MultivalueMap을 만들고 요청 파라미터를 추가 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("orderNo", orderNo+"");
		params.add("orderItemNo", orderItemNo+"");

		// get 방식 요청이므로 params 맵을 그대로 전송
		URI uri = UriComponentsBuilder.fromOriginHeader("http://localhost:8081").path("/orders/order_item_no").queryParams(params).build().toUri();
		ResponseEntity<OrderDto.ReviewInfo> result = restTemplate.getForEntity(uri.toString(), ReviewInfo.class);
		System.out.println(result.getBody());
		model.addAttribute("order", result.getBody());
		return "review/write_review";
	}
	
	@PostMapping("/reviews")
	public ResponseEntity<Void> productReviewWrite(@Valid ReviewDto.Write dto, BindingResult bindingResult, Principal principal, HttpServletRequest req) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		
		service.writeReview(dto, principal.getName());
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("orderItemNo", dto.getOrderItemNo()+"");
		params.add("orderNo", dto.getOrderNo()+"");
		
		URI uri = UriComponentsBuilder.fromOriginHeader("http://localhost:8081").path("/orders/update").queryParams(params).build().toUri();
		ResponseEntity<Integer> result = restTemplate.getForEntity(uri.toString(), Integer.class);
		
		return ResponseEntity.ok(null);
	}
	
	public ResponseEntity<List<Review>> ReviewList(Principal principal) {
		return ResponseEntity.ok(service.getReviewList(principal.getName()));
	}
}

