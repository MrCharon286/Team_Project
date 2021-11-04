package com.example.plantforu.entity.cart;



import java.util.List;

import com.example.plantforu.entity.product.Product;

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
	private List<CartItem> cartItemList;
	
	// 상품을 선택한 경우 상품 정보를 Cart로 복사
	public Cart(Product product) {
		this.pno = product.getPno();
		this.pname = product.getPname();
		this.pprice = product.getPprice();
		this.pcount = 1;
		this.pimage = product.getPimage();
		this.ctotalPrice = pprice;
	}
	
	public void increase() {
		this.pcount++;
		this.ctotalPrice = this.pcount * this.pprice;
	}
	
	public void decrease() {
		if(this.pcount>1)
			this.pcount--;
		this.ctotalPrice = this.pcount * this.pprice;
	}

	public OrderItem toCartOrderItem() {
		return OrderItem.builder().pname(pname).pprice(pprice).pcount(pcount).orderItemPrice(ctotalPrice).pimage(pimage).build();
	}
}
