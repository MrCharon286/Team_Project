package com.example.plantforu.controller;

import java.util.*;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.plantforu.entity.order.*;
import com.example.plantforu.service.*;

import lombok.*;

@RequiredArgsConstructor
@Controller
public class CartController {
	private final CartService service;

	@Secured("ROLE_USER")
	@GetMapping("/cart/view")
	public void view() {
	}	
	
	@PostMapping("/carts")
	public ResponseEntity<Void> add(@RequestBody Cart cart) {
		service.add(cart);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping(path="/carts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cart>> read() {
		return ResponseEntity.ok(service.read());
	}

	@PatchMapping(path="/carts/increase", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cart>> increase(Integer pno) {
		return ResponseEntity.ok(service.increase(pno));
	}
	
	@PatchMapping(path="/carts/decrease", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cart>> decrease(Integer pno) {
		return ResponseEntity.ok(service.decrease(pno));
	}
	
	@DeleteMapping(path="/carts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cart>> delete(@RequestBody List<Integer> dtos) {
		return ResponseEntity.ok(service.delete(dtos));
	}
}
