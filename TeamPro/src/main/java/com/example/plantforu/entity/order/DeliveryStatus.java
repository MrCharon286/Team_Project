package com.example.plantforu.entity.order;

import com.fasterxml.jackson.annotation.*;

public enum DeliveryStatus {
	PAY("결제완료"), SHIPPING("배송중"), CANCEL("취소");

	@JsonValue
	private String korean;
	
	private DeliveryStatus(String korean) {
		this.korean = korean;
	}
}
