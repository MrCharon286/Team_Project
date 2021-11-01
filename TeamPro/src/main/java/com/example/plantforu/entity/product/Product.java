package com.example.plantforu.entity.product;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)
@Entity
@DynamicUpdate
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="product_seq")
	@SequenceGenerator(name="product_seq", sequenceName="product_seq", allocationSize=1)
	private Integer pno;
	
	@Lob
	private String pdetail;
	
	@Lob
	private String pimage;	
	
	@Column(length=20)
	private String pname;
	
	private Integer pprice;
	
	@Enumerated(EnumType.STRING)
	@Column(name="category")
	private Category category;
}
