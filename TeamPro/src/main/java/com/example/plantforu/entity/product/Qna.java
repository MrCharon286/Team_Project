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
@IdClass(ReviewId.class)
@Entity
public class Qna extends BaseCreateTimeEntity {
	@Id
	@ManyToOne
	@JoinColumn(name="pno")
	@JsonIgnore
	private Product product;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="qna_seq")
	@SequenceGenerator(name="qna_seq", sequenceName="qna_seq", allocationSize=1)
	private Integer qno;
	
	@Column(length=30)
	private String qtitle;
	
	@Lob
	private String qcontent;
	
	@Column(length=200)
	private String qnacontent;
}
