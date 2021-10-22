package com.example.plantforu.entity.seller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)
@Entity
@DynamicUpdate
public class Seller {
	@PrePersist
	public void init() {

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
	
	@Column(length = 20)
	@JsonIgnore
	private String sauthority;

}