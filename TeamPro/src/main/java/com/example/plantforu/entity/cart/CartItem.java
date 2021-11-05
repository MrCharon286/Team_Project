package com.example.plantforu.entity.cart;

import lombok.*;

@Data
@AllArgsConstructor
public class CartItem {
	private String pname;
	private Integer pprice;
	private Integer pcount;
	private String pimage;
	private Integer itemPrice;
	public void increase() {
		this.pcount++;
		this.itemPrice = this.pcount * this.pprice;
	}
	public void decrease() {
		if(this.pcount>1)
			this.pcount--;
		this.itemPrice = this.pcount * this.pprice;
	}
	
	
}
