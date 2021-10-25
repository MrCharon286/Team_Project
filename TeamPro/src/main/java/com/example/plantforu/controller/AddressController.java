package com.example.plantforu.controller;

import java.security.*;
import java.util.*;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.example.plantforu.controller.dto.AddressDto;
import com.example.plantforu.entity.order.*;
import com.example.plantforu.service.*;

import lombok.*;

@Secured("ROLE_USER")
@RequiredArgsConstructor
@RestController
public class AddressController {
	private final AddressService service;
	
	@GetMapping(path="/address", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Address>> readAddresses(Principal principal) {
		return ResponseEntity.ok(service.readAddresses(principal.getName()));
	}
	
	@PostMapping(path="/address/new", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> add(AddressDto.Add dto, Principal principal) {
		return ResponseEntity.ok(service.add(dto, principal.getName()));
	}
}
