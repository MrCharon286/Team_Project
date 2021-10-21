package com.example.plantforu.entity.seller;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "seller")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(SellerAuthorityId.class)
public class SellerAuthority {
	@Id
	@ManyToOne
	@JoinColumn(name="selleremail")
	private Seller seller;
	
	@Id
	private String sellerauthorityName;
}
