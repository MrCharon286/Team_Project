package com.example.plantforu.entity.cart;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Builder
@IdClass(AddressId.class)
@Entity
public class Address {
	@Id
	@Column(length=10)
	private String username;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_seq")
	@SequenceGenerator(name="address_seq", sequenceName="address_seq", allocationSize=1)
	private Integer ano;
	/*
	@Id
	@Column(length=10)
	private String nickname;
	*/
	
	@Column(length=5)
	private String zipcode;
	
	@Column(length=50)
	private String address1;
	
	@Column(length=50)
	private String address2;
	
	private boolean isDefault;
}
