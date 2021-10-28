 package com.example.plantforu.entity.order;

import javax.persistence.*;

import com.example.plantforu.entity.*;
import com.example.plantforu.entity.product.Product;
import com.example.plantforu.util.*;
import com.fasterxml.jackson.annotation.*;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@ToString(exclude="order")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Builder
@IdClass(OrderItemId.class)
@Entity
public class OrderItem {
	@PrePersist
	private void init() {
		this.isReviewAvailable = true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="order_item_seq")
	@SequenceGenerator(name="order_item_seq", sequenceName="order_item_seq", allocationSize=1)
	private Integer orderItemNo;
	
	@Id
	@ManyToOne
	@JoinColumn(name="ono")
	@JsonIgnore
	private Order order;
	
	@Id
	@ManyToOne
	@JoinColumn(name="pno")
	@JsonIgnore
	private Product product;
	
	@Column(length=20)
	private String pname;
		
	private Integer pprice;
	
	private Integer pcount;
	
	private Integer ototalPrice;
	
	@Column(length=20)
	private String pimage;
	
	private Boolean isReviewAvailable;
}
