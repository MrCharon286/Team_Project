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
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class isReviewAvailable {
		private Integer ono;
		@JsonProperty("orderday")
		@JsonFormat(pattern="yyyy년 MM월 dd일")
		private LocalDateTime createTime;
		private String image;
		private String pname;
		private Integer pno;
		private Integer orderItemNo;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ReviewInfo {
		private String orderday;
		private String pname;
		private Integer pno;
		private Integer ono;
		private Integer orderItemNo;
	}
}
