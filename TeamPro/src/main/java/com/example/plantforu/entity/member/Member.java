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
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="authorities")
@Builder
@Accessors(chain=true)
@Entity
@DynamicUpdate
public class Member {
	@PrePersist
	public void init() {
		userpoint = 0;
		loginFailCnt = 0;
		enabled = false;
	}
	
	@Id
	@Column(length = 30)
	private String useremail;
	
	@Column(length = 20)
	@JsonIgnore
	private String password;
	
	@Column(length = 5)
	private String username;
	
	@Column(length = 13)
	private String usertel;
	
	private Integer userpoint; 
	
	@JsonIgnore
	private Integer loginFailCnt;
	
	@JsonIgnore
	private Boolean enabled;
	
	@JsonIgnore
	@Column(length=20)
	private String checkcode;
	
	@JsonIgnore
	@OneToMany(mappedBy="member", cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private Set<Authority> authorities;

	public void addJoinInfo(String checkcode, String encodedPassword, List<String> authorities) {
		if(this.authorities==null)
			this.authorities = new HashSet<Authority>();
		this.checkcode = checkcode;
		this.password = encodedPassword;
		authorities.forEach(authorityName->this.authorities.add(new Authority(this, authorityName)));
	}
}
