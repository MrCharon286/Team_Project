package com.example.plantforu.entity.member;

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
@ToString(exclude="authorities")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)
@Entity
@DynamicUpdate
public class Member {
	@PrePersist
	public void init() {
		enabled = false;
	}

	@Id
	@Column(length=30)
	private String useremail;

	@Column(length=80)
	@JsonIgnore
	private String password;

	@Column(length=5)
	private String userirum;

	@Column(length=13)
	private String usertel;


	@JsonIgnore
	@OneToMany(mappedBy="member", cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private Set<Authority> authorities;
	
	@JsonIgnore
	private boolean enabled;
	
	@JsonIgnore
	@Column(length=20)
	private String checkcode;
	
	public void addJoinInfo(String checkcode, String encodedPassword, List<String> authorities) {
		if(this.authorities==null)
			this.authorities = new HashSet<Authority>();
		this.checkcode = checkcode;
		this.password = encodedPassword;
		authorities.forEach(authorityName->this.authorities.add(new Authority(this, authorityName)));
	}

	public void loginSuccess() {
		this.enabled = true;
	}

}