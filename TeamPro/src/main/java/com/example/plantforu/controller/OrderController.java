package com.example.plantforu.controller;

import java.net.*;
import java.security.*;
import java.util.*;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

import com.example.plantforu.controller.dto.OrderDto;
import com.example.plantforu.entity.order.*;
import com.example.plantforu.service.*;

import lombok.*;

@RequiredArgsConstructor
@Controller
public class OrderController {
	private final OrderService service;
	
	// 장바구니에서 주문을 선택하면 선택한 주문 목록을 다시 확인하는 페이지
	@GetMapping("/order/read")
	public void read() {
	}

	// 새 배송지 추가 화면을 띄우는 메서드
	@GetMapping("/order/new_address")
	public void newAddress() {
	}
	
	// 주문 완료 후 이동
	@GetMapping("/order/list")
	public void list() {
	}

	
	// 장바구니에서 선택하고 주문 버튼 누르면 선택한 장바구니 물품을 주문 목록으로 이동
	@PostMapping("/orders/cart")
	public ResponseEntity<Void> orderCart(@RequestBody List<Integer> pnos) {
		service.orderCart(pnos);
		URI uri = UriComponentsBuilder.newInstance().path("/order/read").queryParam("select", "cart").build().toUri();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Location", uri.toString());
		return ResponseEntity.ok().headers(responseHeaders).body(null);
	}
	
	// 상품 화면에서 바로 주문을 선택
	@PostMapping("/orders/product")
	public ResponseEntity<Void> orderProduct(@RequestBody OrderDto.OrderProduct dto) {
		service.orderProduct(dto);
		URI uri = UriComponentsBuilder.newInstance().path("/order/read").queryParam("select", "product").build().toUri();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Location", uri.toString());
		return ResponseEntity.ok().headers(responseHeaders).body(null);
	}
	
	// 장바구니에서 선택 또는 상품에서 선택해 결제하려는 목록 보여주기
	@GetMapping(path="/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderItem>> getOrders(String select) {
		return ResponseEntity.ok(service.getOrders(select));
	}

	@PostMapping("/orders/payment")
	public ResponseEntity<Void> payment(String select, Integer ano, Principal principal) {
		service.payment(select, ano, principal.getName());
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/orders/payment")
	public ResponseEntity<List<Order>> readAll(Principal principal) {
		return ResponseEntity.ok(service.readAll(principal.getName()));
	}
	
	// /review/available_list의 요청을 받아 리뷰가능한 주문 목록을 리턴
	@Secured("ROLE_USER")
	@GetMapping(path="/orders/review_available", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDto.isReviewAvailable>> isReviewedList(Principal principal) { 
		return ResponseEntity.ok(service.isReviewAvailableList(principal.getName()));
	}
	
	@GetMapping(path="/orders/order_itemno", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDto.ReviewInfo> getOrderItem(Integer ono, Integer orderItemNo) {
		return ResponseEntity.ok(service.getOrderItem(ono, orderItemNo));
	}
	
	@GetMapping("/orders/update")
	public ResponseEntity<Integer> setIsReviewAvailableToFalse(Integer orderNo, Integer orderItemNo) {
		service.setIsReviewAvailableToFalse(orderNo, orderItemNo);
		return ResponseEntity.ok(orderItemNo);
	}
}