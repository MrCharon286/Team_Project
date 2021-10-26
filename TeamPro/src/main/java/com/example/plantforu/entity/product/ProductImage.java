package com.example.plantforu.entity.product;

import javax.persistence.*;
import javax.persistence.Entity;

import com.example.plantforu.entity.*;
import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Getter
@Setter
@ToString(exclude="product")
@EqualsAndHashCode(exclude= "product", callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(ProductImageId.class)
@Entity
public class ProductImage extends BaseCreateTimeEntity {
	@Id
	@ManyToOne
	@JoinColumn(name="pno")
	@JsonIgnore
	private Product product;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pimageno_seq")
	@SequenceGenerator(name="pimageno_seq", sequenceName="pimageno_seq", allocationSize=1)
	private Integer pimageno;
	
	@Column(length=20)
	private String pprofile;
}
