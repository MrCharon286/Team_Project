package com.example.plantforu.entity.order;

import java.io.*;

import lombok.*;

@SuppressWarnings("unused")
@EqualsAndHashCode
public class OrderItemId implements Serializable {
	private Integer order;
	private Integer ono;
	private Integer pno;
}
