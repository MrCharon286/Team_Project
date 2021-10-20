 package com.example.plantforu.entity.order;

import javax.persistence.*;

import com.example.plantforu.entity.*;
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
	@ManyToOne
	@JoinColumn(name="orderNo")
	@JsonIgnore
	private Order order;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="order_item_seq")
	@SequenceGenerator(name="order_item_seq", sequenceName="order_item_seq", allocationSize=1)
	private Integer orderItemNo;
	
	private Integer pno;
	
	@Column(length=20)
	private String pname;
	
	@Column(length=20)
	private String manufacturer;
	
	private Integer price;
	
	private Integer count;
	
	private Integer orderItemPrice;
	
	@Column(length=100)
	private String image;
	
	private Boolean isReviewAvailable;

}
