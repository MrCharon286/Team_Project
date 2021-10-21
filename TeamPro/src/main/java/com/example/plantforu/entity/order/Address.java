package com.example.plantforu.entity.order;

import javax.persistence.*;

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
	@Column(length=30)
	private String useremail;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_seq")
	@SequenceGenerator(name="address_seq", sequenceName="address_seq", allocationSize=1)
	private Integer ano;
	
	//여기부터
	@Id
	@Column(length=10)
	private String nickname;	//닉네임은 없습니다 확인부탁드립니다.
	
	@Column(length=7)
	private String acode;	//혹시 우편 번호인지?
	
	//여기까지 우린 닉네임없고 acode라는 물리이름을 가진에 없습니다 확인부탁드립니다.
	
	@Column(length=30)
	private String address1;
	
	@Column(length=30)
	private String address2;
	
	private boolean isDefault;		//isDefault 는 어떤걸 의미하는지요?
}
