package com.example.plantforu.controller.dto;

import java.time.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class OrderDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class OrderProduct {
		private Integer pno;
		private Integer pcount;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class List {
		private Integer pno;
		private Integer pcount;
	}
	
}
