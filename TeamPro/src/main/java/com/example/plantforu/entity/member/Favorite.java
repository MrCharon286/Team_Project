package com.example.plantforu.entity.member;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.plantforu.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
	@ManyToOne
	@JoinColumn(name="pno")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="useremail")
	private Member member;
}
