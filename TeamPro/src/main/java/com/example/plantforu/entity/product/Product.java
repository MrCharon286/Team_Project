package com.example.plantforu.entity.product;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;

import com.example.plantforu.entity.*;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@ToString(exclude="ctgno")
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)
@Entity
@DynamicUpdate
public class Product extends BaseCreateTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="product_seq")
	@SequenceGenerator(name="product_seq", sequenceName="product_seq", allocationSize=1)
	private Integer pno;
	
	@Lob
	private String pdetail;
	
	@Column(length=20)
	private String pname;
	
	@Column(length=100)
	private String pimage;
	
	private Integer pprice;
	
	@JoinColumn(name="ctgno")
	private Integer ctgno;
	
	@PrePersist
	public void init() {
		this.pprice = 0;
	}
}
