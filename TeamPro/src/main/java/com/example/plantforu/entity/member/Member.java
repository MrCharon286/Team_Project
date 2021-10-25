package com.example.plantforu.entity.member;

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

	@Column(length = 20)
	@JsonIgnore
	private String authority;
}