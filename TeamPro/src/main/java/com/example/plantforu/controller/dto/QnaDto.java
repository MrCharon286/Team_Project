package com.example.plantforu.controller.dto;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class QnaDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Write {
		private Integer pno;
		private String qtitle;
		private String qcontent;
		private String qnacontent;
	}
}
