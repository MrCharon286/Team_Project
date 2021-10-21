package com.example.plantforu.entity.order;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class Cart {
	private Integer pno;
	private String pname;
	private String manufacturer;
	private Integer price;
	private Integer count;
	private String pimageno;
	private Integer cprice;
	
	public void increase() {
		this.count++;
		this.cprice = this.count * this.price;
	}
	
	public void decrease() {
		if(this.count>1)
			this.count--;
		this.cprice = this.count * this.price;
	}

	public OrderItem toOrderItem() {
		return OrderItem.builder().pno(pno).pname(pname).manufacturer(manufacturer).price(price).count(count).orderItemPrice(cprice).pimageno(pimageno).build();
	}
}
