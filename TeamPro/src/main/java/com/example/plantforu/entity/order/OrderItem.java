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
		this.isreviewd = true;
	}
	
	@Id
	@ManyToOne
	@JoinColumn(name="ono")
	@JsonIgnore
	private Order order;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="order_item_seq")
	@SequenceGenerator(name="order_item_seq", sequenceName="order_item_seq", allocationSize=1)
	private Integer ono;
	
	private Integer pno;
	
	@Column(length=20)
	private String pname;
	
	@Column(length=20)		
	private Integer pprice;
	
	private Integer pcount;
	
	private Integer odetailPrice;
	
	@Column(length=100)
	private String pimageno;
	
	private Boolean isreviewd;

}
