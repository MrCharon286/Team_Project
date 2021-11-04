package com.example.plantforu.service;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlantforuException {	
	public static class ProductNotFoundException extends RuntimeException {
	}

	public static class OutOfStockException extends RuntimeException {
	}

	public static class OrderNotExistException extends RuntimeException {
	}

	public static class OrderItemNotExistException extends RuntimeException {
	}
}
