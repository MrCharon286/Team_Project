package com.example.plantforu.entity.product;

import javax.persistence.*;

import com.example.plantforu.entity.*;
import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Getter
@Setter
@ToString(exclude="product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(ReviewId.class)
@Entity
public class Review extends BaseCreateTimeEntity {
	@Id
	@ManyToOne
	@JoinColumn(name="pno")
	@JsonIgnore
	private Product product;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="review_seq")
	@SequenceGenerator(name="review_seq", sequenceName="review_seq", allocationSize=1)
	private Integer rno;
	
	@Column(length=100)
	private String rcontent;
	
	private Integer rating;
}
