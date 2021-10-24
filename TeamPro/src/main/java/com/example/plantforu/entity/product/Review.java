package com.example.plantforu.entity.product;

import javax.persistence.*;

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
	
	//username 추가 필요?(테이블에서 조인받는 형식이 아니라 loginId를 읽어오는 형식으로)
	
	@Column(length=100)
	private String rcontent;
	
	private Integer rating;
}
