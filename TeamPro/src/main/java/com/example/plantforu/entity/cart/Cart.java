package com.example.plantforu.entity.cart;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class Cart {
	private Integer pno;
	private String pname;	
	private Integer pprice;
	private Integer pcount;
	private String pimage;
	private Integer ctotalPrice;
	
	public void increase() {
		this.pcount++;
		this.ctotalPrice = this.pcount * this.pprice;
	}
	
	public void decrease() {
		if(this.pcount>1)
			this.pcount--;
		this.ctotalPrice = this.pcount * this.pprice;
	}

	public OrderItem toOrderItem() {
		return OrderItem.builder().pname(pname).pprice(pprice).pcount(pcount).ototalPrice(ctotalPrice).pimage(pimage).build();
	}
}
