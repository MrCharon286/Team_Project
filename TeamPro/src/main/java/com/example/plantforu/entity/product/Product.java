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
	
<<<<<<< HEAD
	private Boolean papproval;
	
	private String pimage;
	
	@OneToMany(mappedBy="product", cascade= {CascadeType.REMOVE})
	private Set<Review> reviews;
	
	@OneToMany(mappedBy="product", cascade= {CascadeType.REMOVE})
	private Set<Qna> qnas;
	
=======
>>>>>>> a7abce299e559838aac2f38524f9b2d40b0607e5
	@JoinColumn(name="ctgno")
	private Integer ctgno;
	
	@PrePersist
	public void init() {
		this.pprice = 0;
	}
}
