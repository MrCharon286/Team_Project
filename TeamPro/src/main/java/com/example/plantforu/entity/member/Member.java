package com.example.plantforu.entity.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)
@Entity
@DynamicUpdate
public class Member {
	@PrePersist
	public void init() {
		loginFailCnt = 0;
		enabled = false;
	}

	@Id
	@Column(length = 30, name="user_email")
	private String useremail;

	@Column(length = 20)
	@JsonIgnore
	private String password;

	@Column(length = 5)
	private String userirum;

	@Column(length = 13)
	private Integer usertel;


	@Column(length = 20)
	@JsonIgnore
	private String authority;
	//권한 부여?
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		if (this.useremail.equals("user")) {
			auth.add(new SimpleGrantedAuthority("ROLE_USER"));
		} 
		return auth;
	}
	
	@JsonIgnore
	private Integer loginFailCnt;
	
	@JsonIgnore
	private Boolean enabled;
	
	@JsonIgnore
	@Column(length=20)
	private String checkcode;

	public void loginSuccess() {
		this.loginFailCnt = 0;
		this.enabled = true;
	}

	public void loginFail() {
		if(this.loginFailCnt<4)
			this.loginFailCnt++;
		else {
			this.loginFailCnt++;
			this.enabled = false;
		}
	}
}