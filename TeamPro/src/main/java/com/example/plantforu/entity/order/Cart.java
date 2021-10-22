package com.example.plantforu.entity.order;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class Cart {
	private Integer pno;
	private String pname;	
	private Integer pprice;
	private Integer pcount;
	private String pimageno;
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
		return OrderItem.builder().pno(pno).pname(pname).pprice(pprice).pcount(pcount).odetailPrice(ctotalPrice).pimageno(pimageno).build();
	}
}
