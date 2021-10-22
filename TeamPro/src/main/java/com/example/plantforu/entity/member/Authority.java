package com.example.plantforu.entity.member;

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
@ToString(exclude = "member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(AuthorityId.class)
public class Authority {
	@Id
	@ManyToOne
	@JoinColumn(name="useremail")
	private Member member;

	@Id
	private String authorityName;
}