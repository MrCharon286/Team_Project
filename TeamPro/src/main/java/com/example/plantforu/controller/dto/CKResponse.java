package com.example.plantforu.controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class CKResponse {
	private Integer uploaded;
	private String fileName;
	private String url;
}
