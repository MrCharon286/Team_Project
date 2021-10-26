package com.example.plantforu.entity.member.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class BindExceptionMessage {
	private String fieldName;
	private String errorMessage;
}
