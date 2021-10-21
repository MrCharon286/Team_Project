package com.example.plantforu.entity.seller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="sellerauthorities")
@Builder
@Accessors(chain=true)
@Entity
@DynamicUpdate
public class Seller {
	@PrePersist
	public void init() {
		sloginFailCnt = 0;
		senabled = false;
	}
	
	@Id
	@Column(length = 12)
	private String sellerid;
	
	@Column(length = 20)
	@JsonIgnore
	private String spassword;
	
	@Column(length = 13)
	private String stele;
	
	@Column(length = 20)
	private String scompany;
	
	@Column(length =  5)
	private String sname; 
	
	@Column(length = 50)
	private String saddress;
	
	@JsonIgnore
	private Integer sloginFailCnt;
	
	@JsonIgnore
	private Boolean senabled;
	
	@JsonIgnore
	@Column(length=20)
	private String scheckcode;
	
	@JsonIgnore
	@OneToMany(mappedBy="seller", cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private Set<SellerAuthority> sellerauthorities;

	public void selleraddJoinInfo(String scheckcode, String sencodedPassword, List<String> sellerauthorities) {
		if(this.sellerauthorities==null)
			this.sellerauthorities = new HashSet<SellerAuthority>();
		this.scheckcode = scheckcode;
		this.spassword = sencodedPassword;
		sellerauthorities.forEach(sellerauthorityName->this.sellerauthorities.add(new SellerAuthority(this, sellerauthorityName)));
	}
}
