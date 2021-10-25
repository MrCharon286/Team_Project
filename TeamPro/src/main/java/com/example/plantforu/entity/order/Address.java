package com.example.plantforu.entity.order;

import javax.persistence.*;

import com.example.plantforu.entity.member.Member;

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
	@ManyToOne
	@JoinColumn(name="useremail")
	private Member member;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_seq")
	@SequenceGenerator(name="address_seq", sequenceName="address_seq", allocationSize=1)
	private Integer ano;
	
	@Id
	@Column(length=10)
	private String username;
	
	@Column(length=7)
	private String anum;
	
	@Column(length=30)
	private String address1;
	
	@Column(length=30)
	private String address2;
	
	private boolean isDefault;

	public Address setUseremail(String useremail) {
		// TODO Auto-generated method stub
		return null;
	}
}
