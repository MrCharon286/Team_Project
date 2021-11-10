package com.example.plantforu.entity.member;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@ToString(exclude="member")
@EqualsAndHashCode(exclude={"member"})
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
