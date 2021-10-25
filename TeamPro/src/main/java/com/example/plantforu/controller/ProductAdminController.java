package com.example.plantforu.controller;

import java.util.*;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.service.*;

import lombok.*;

@RequiredArgsConstructor
@Controller
public class ProductAdminController {
	private final ProductAdminService service;
	
	@Secured("ROLE_ADMIN")
	@GetMapping(path="/product/list_disapproval", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDto.DisApproved>> DisApprovedList() { 
		return ResponseEntity.ok(service.disApprovedList());
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/products/update")
	public ResponseEntity<Integer> setDisApprovedToApproved(Integer pno) {
		service.setDisApprovedToApproved(pno);
		return ResponseEntity.ok(pno);
	}
}
