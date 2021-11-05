package com.example.plantforu.entity.cart;

import java.io.*;

import lombok.*;

@SuppressWarnings("unused")
@EqualsAndHashCode
public class OrderItemId implements Serializable {
	private Integer order;
	private Integer orderItemNo;
}
