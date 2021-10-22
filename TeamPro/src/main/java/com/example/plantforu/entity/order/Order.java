package com.example.plantforu.entity.order;


import java.util.*;

import javax.persistence.*;

import com.example.plantforu.entity.*;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@ToString(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Builder
@Entity
@Table(name="orders", indexes=@Index(name="order_idx_orderer", columnList="orderer"))
public class Order extends BaseCreateTimeEntity  {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="order_seq")
	@SequenceGenerator(name="order_seq", sequenceName="order_seq", allocationSize=1)
	private Integer ono;
	
	private Integer pprice;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="orderer", referencedColumnName="useremail"), @JoinColumn(name="ano", referencedColumnName="ano")})
	private Address address;

	@Enumerated(EnumType.ORDINAL)
	private DeliveryStatus deliveryStatus;
	
	@OneToMany(mappedBy="order", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<OrderItem> orderItems;
	
	public void addOrderItem(OrderItem orderItem) {
		if(this.orderItems==null)
			this.orderItems = new ArrayList<>();
		this.pprice = this.pprice==null? 0 : this.pprice;
		orderItem.setOrder(this);
		this.orderItems.add(orderItem);
		this.pprice += orderItem.getOrderItemPrice();
	}
}
