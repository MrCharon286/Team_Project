package com.example.plantforu.controller.dto;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class ReviewDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Write {
		private Integer pno;
		private Integer orderNo;
		private Integer orderItemNo;
		private String rcontent;
		private Integer rating;
	}
}
