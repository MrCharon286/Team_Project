package com.example.plantforu.entity.product;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="category_seq")
	@SequenceGenerator(name="category_seq", sequenceName="category_seq", allocationSize=1)
	@Column(name="category_no")
	private Integer ctgno;
	
	@Column(name="category_name", length=10)
	private String ctgName;
}
