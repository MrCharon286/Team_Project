package com.example.plantforu.entity.order;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Builder
@IdClass(Aname.class)
@Entity
public class Address {
	@Id
	@Column(length=30)
	private String useremail;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_seq")
	@SequenceGenerator(name="address_seq", sequenceName="address_seq", allocationSize=1)
	private Integer ano;
	
	
	
	@Column(length=7)
	private String acode;
	
	@Column(length=30)
	private String address1;
	
	@Column(length=30)
	private String address2;
	
	private boolean isDefault;
}
