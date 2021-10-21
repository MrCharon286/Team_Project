package com.example.plantforu.entity.product;

import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
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
	
	private Integer pcountOfRating;
	
	private Integer ptotalOfRating;
	
	private Double pavgOfRating;
	
	private Integer pstock;
	
	private Integer pprice;
	
	private Boolean papproval;
	
	@OneToMany(mappedBy="product", cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Review> reviews;
	
	@ManyToOne
	@JoinColumn(name="ctgno")
	private Integer ctgno;
	
	private String sellerId;
	
	@PrePersist
	public void init() {
		this.pcountOfRating = 0;
		this.ptotalOfRating = 0;
		this.pavgOfRating = 0.0;
		this.pstock = 0;
		this.pprice = 0;
	}
	
	public void addReview(Review review) {
		if(this.reviews==null)
			this.reviews = new HashSet<>();
		reviews.add(review);
		this.pcountOfRating++;
		this.ptotalOfRating+=review.getRating();
		this.pavgOfRating=this.ptotalOfRating/(double)this.pcountOfRating;	
	}
}
